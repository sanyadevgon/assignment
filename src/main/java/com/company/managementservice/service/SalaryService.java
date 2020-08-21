package com.company.managementservice.service;

import com.company.managementservice.exception.EmptyBodyException;
import com.company.managementservice.exception.MethodArgumentNotValidException;
import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.KafkaDto;
import com.company.managementservice.model.dto.SalaryDto;
import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.entity.Organisation;
import com.company.managementservice.model.entity.Salary;
import com.company.managementservice.repo.DepartmentRepo;
import com.company.managementservice.repo.EmployeeRepo;
import com.company.managementservice.repo.OrganisationRepo;
import com.company.managementservice.repo.SalaryRepo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
public class SalaryService {

    @Autowired
    CurrencyConvertorService currencyConvertorService;

    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private OrganisationRepo organisationRepo;

    @Autowired
    private CacheService cacheService;

    private ModelMapper modelMapper = new ModelMapper();

    public SalaryDto saveSalary(Long id, SalaryDto salaryDto) throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-" + id);
        if (employee.get().getTerminatedDate() != null)
            throw new NotFoundException("Employee is terminated,salary cant be updated");
        Set<Salary> salaries = employee.get().getSalaries();
        if (!salaries.isEmpty())
            throw new NotFoundException("Salary details already exists, try updating the salary details");
        Long salaryInRuppe =
                currencyConvertorService.getRupeeValue(Long.valueOf(salaryDto.getAmount()), salaryDto.getCurrency());
        Salary salary = new Salary();
        salary.setAmount(Math.toIntExact(salaryInRuppe));
        salary.setCurrency("RUPEES");
        salary.setFromDate(LocalDate.now());
        salaries.add(salary);
        salaryRepo.save(salary);
        employee.get().setSalaries(salaries);
        employeeRepo.save(employee.get());
        return modelMapper.map(salary,SalaryDto.class);
    }

    public List<SalaryDto> getEmployeeSalary(Long id) throws NotFoundException {

        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id- " + id);
        Set<Salary> salaries = employee.get().getSalaries();
        return salaries.stream().map(e -> modelMapper.map(e, SalaryDto.class)).collect(Collectors.toList());

    }

    @CachePut(cacheNames = "salary", key = "#employeeId")
    public SalaryDto UpdateSalary(SalaryDto salaryDto, Long employeeId) throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id- " + employeeId);
        Set<Salary> salaries = employee.get().getSalaries();
        if (salaries.isEmpty())
            throw new NotFoundException("Salary details does not exists, first set the salary details");
        if (employee.get().getTerminatedDate() != null)
            throw new NotFoundException("Employee is terminated, salary cant be updated");
        log.info("salaryService: UpdateSalary: update the salary of employee in db :{}", employeeId);
        for (Salary s: salaries) {
            if (s.getToDate() == null) {
                s.setToDate(LocalDate.now());
            }
        }
        Long salaryInRuppe =
                currencyConvertorService.getRupeeValue(Long.valueOf(salaryDto.getAmount()), salaryDto.getCurrency());
        Salary salary = new Salary();
        salary.setAmount(Math.toIntExact(salaryInRuppe));
        salary.setCurrency("RUPEES");
        salary.setFromDate(LocalDate.now());
        salaries.add(salary);
        employee.get().setSalaries(salaries);
        salaryRepo.save(salary);
        employeeRepo.save(employee.get());
        return modelMapper.map(salary,SalaryDto.class);

    }

    @Cacheable(cacheNames = "salary", key = "#employeeId")
    public SalaryDto getEmployeeCurrentSalary(Long employeeId) throws NotFoundException {

        Optional<Employee> employee = employeeRepo.findById(employeeId);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id- " + employeeId);
        if (employee.get().getTerminatedDate() != null)
            throw new NotFoundException("Employee is terminated, no current salary found");
        log.info("salaryService: getEmployeeCurrentSalary: get the current salary of employee from db :{}", employeeId);
        Salary currentSalary = null;
        Set<Salary> salaries = employee.get().getSalaries();
        for (Salary s: salaries) {
            if (s.getToDate() == null) {
                currentSalary = s;
            }
        }
        if (currentSalary == null) {
            throw new NotFoundException("No Salary Details found");
        }
        return modelMapper.map(currentSalary, SalaryDto.class);
    }

    public void updateSalaryByDepartment(Long inc, String currency, Long departmentId)
            throws NotFoundException, MethodArgumentNotValidException {
        if (Objects.isNull(inc) || inc <= 0)
            throw new MethodArgumentNotValidException("invalid data for increment ");
        Optional<Department> department = departmentRepo.findById(departmentId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-{} " + departmentId);
        Long increment = currencyConvertorService.getRupeeValue(inc, currency);
        employeeRepo.updateSalaryByDepartment(increment, departmentId);
        List<Long> employeesId = employeeRepo.getEmployeeId(departmentId);
        for (Long employeeId: employeesId) {
            cacheService.removeSalaryCacheEmp(employeeId);
            cacheService.removeSalaryCacheDept(departmentId);
        }
    }

    public void updateSalaryByDepartmentPercentage(Long percentage, Long departmentId)
            throws NotFoundException, MethodArgumentNotValidException {
        if (Objects.isNull(percentage) || percentage == 0 || percentage < -100 || percentage > 100)
            throw new MethodArgumentNotValidException("Percent not in range 0 to 100,-100 to 0 ");
        Optional<Department> department = departmentRepo.findById(departmentId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-{} " + departmentId);
        employeeRepo.updateSalaryByDepartmentPercentage(percentage, departmentId);
        List<Long> employeesId = employeeRepo.getEmployeeId(departmentId);
        for (Long employeeId: employeesId) {
            cacheService.removeSalaryCacheEmp(employeeId);
            cacheService.removeSalaryCacheDept(departmentId);
        }
    }

    public void updateSalaryByOrganisation(Long inc, String currency, Integer organisationId)
            throws NotFoundException, MethodArgumentNotValidException {
        if (Objects.isNull(inc) || inc <= 0)
            throw new MethodArgumentNotValidException("invalid data for increment ");
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id-{} " + organisationId);
        Long increment = currencyConvertorService.getRupeeValue(inc, currency);
        employeeRepo.updateSalaryByOrganisation(increment, organisationId);
        List<Long> employeesId = employeeRepo.getEmployeeIdOrgan(organisationId);
        for (Long employeeId: employeesId) {
            cacheService.removeSalaryCacheEmp(employeeId);
        }
    }

    public void updateSalaryByOrganisationPercentage(Long percentage, Integer organisationId)
            throws NotFoundException, MethodArgumentNotValidException {
        if (Objects.isNull(percentage) || percentage == 0 || percentage < -100 || percentage > 100)
            throw new MethodArgumentNotValidException("Percent not in range 0 to 100,-100 to 0 ");
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id-{} " + organisationId);
        employeeRepo.updateSalaryByOrganisationPercentage(percentage, organisationId);
        List<Long> employeesId = employeeRepo.getEmployeeIdOrgan(organisationId);
        for (Long employeeId: employeesId) {
            cacheService.removeSalaryCacheEmp(employeeId);
        }
    }

    @Transactional
    public void updateSalaryThroughKafka(KafkaDto kafkaDto) throws NotFoundException, EmptyBodyException {
        Long employeeId = kafkaDto.getId();
        if (employeeId==null) {
            if(kafkaDto.getFirstName()==null){
                throw new EmptyBodyException("Please provide firstName");
            }
            Employee employee1 = new Employee();
            employee1.setFirstName(kafkaDto.getFirstName());
            employee1.setLastName(kafkaDto.getLastName());
            employee1.setDesignationType(kafkaDto.getDesignationType());
            employee1.setIsActive(true);
            employee1.setPhone(kafkaDto.getPhone());
            employee1.setAge(kafkaDto.getAge());
            employee1.setAddress(kafkaDto.getAddress());
            employee1.setEmailId(kafkaDto.getEmailId());
            Long salaryInRuppe =
                    currencyConvertorService.getRupeeValue(Long.valueOf(kafkaDto.getAmount()), kafkaDto.getCurrency());
            Salary salary = new Salary();
            salary.setAmount(Math.toIntExact(salaryInRuppe));
            salary.setCurrency("RUPEES");
            salary.setFromDate(LocalDate.now());
            Set<Salary> salaries = new HashSet<Salary>();
            salaries.add(salary);
            employee1.setSalaries(salaries);
            employeeRepo.save(employee1);
        } else {
            Optional<Employee> employee = employeeRepo.findById(employeeId);
            if(!employee.isPresent())
                throw new NotFoundException("Employee not found");
            if (employee.get().getTerminatedDate() != null)
                throw new NotFoundException("Employee is terminated, salary cant be updated");
            Set<Salary> salaries = employee.get().getSalaries();
            log.info("salaryService: updateSalaryThroughKafka: Kafka update the salary of employee in db :{}",
                     employeeId);
            for (Salary s: salaries) {
                if (s.getToDate() == null) {
                    s.setToDate(LocalDate.now());
                }
            }
            Long salaryInRuppe =
                    currencyConvertorService.getRupeeValue(Long.valueOf(kafkaDto.getAmount()), kafkaDto.getCurrency());
            Salary salary = new Salary();
            salary.setAmount(Math.toIntExact(salaryInRuppe));
            salary.setCurrency("RUPEES");
            salary.setFromDate(LocalDate.now());
            salaries.add(salary);
            salaryRepo.save(salary);
            employee.get().setSalaries(salaries);
            cacheService.updateSalaryCache(employeeId, salary.getId());
        }
    }

}
