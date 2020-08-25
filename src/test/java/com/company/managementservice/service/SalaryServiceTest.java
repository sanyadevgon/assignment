package com.company.managementservice.service;

import com.company.managementservice.exception.MethodArgumentNotValidException;
import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.SalaryDto;
import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.entity.Salary;
import com.company.managementservice.model.enums.CurrencyType;
import com.company.managementservice.repo.DepartmentRepo;
import com.company.managementservice.repo.EmployeeRepo;
import com.company.managementservice.repo.OrganisationRepo;
import com.company.managementservice.repo.SalaryRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class SalaryServiceTest {

    @InjectMocks
    SalaryService salaryService;

    @Mock
    private SalaryRepo salaryRepo;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private DepartmentRepo departmentRepo;

    @Mock
    private OrganisationRepo organisationRepo;

    @Mock
    private CacheService cacheService;

    @Mock
    private CurrencyConvertorService currencyConvertorService;

    @Test
    public void getEmployeeSalary() {
    }

    @Test
    public void updateSalary() {
    }

    @Test
    public void getEmployeeCurrentSalary_shouldReturnAmountAsPassed_success() throws NotFoundException {

        Employee employee = new Employee();
        Salary salary1 = new Salary(100, CurrencyType.RUPEES, LocalDateTime.now(), LocalDateTime.now());
        Salary salary2 = new Salary(1000, CurrencyType.RUPEES, LocalDateTime.now(), null);

        Set salaries = new HashSet<Salary>();
        salaries.add(salary1);
        salaries.add(salary2);

        employee.setSalaries(salaries);

        Mockito.when(employeeRepo.findById(1l)).thenReturn(Optional.of(employee));

        SalaryDto current = salaryService.getEmployeeCurrentSalary(1L);
        assertEquals(Long.valueOf(current.getAmount()), 1000L);
    }

    @Test
    public void getEmployeeCurrentSalary_shouldThrowExceptionWhenEmployeeIdNotFound() throws NotFoundException {

        try {
            Employee employee = new Employee();
            Set salaries = new HashSet<Salary>();
            employee.setSalaries(salaries);

            Mockito.when(employeeRepo.findById(1l)).thenReturn(Optional.of(employee));

            SalaryDto current = salaryService.getEmployeeCurrentSalary(1L);
        }
        catch (NotFoundException e){
            String message="No Salary Details found";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void updateSalaryByDepartment_shouldThrowExceptionFor1stParamIncrementWhenNull()
            throws NotFoundException{
        try {
            salaryService.updateSalaryByDepartment(null, "rupees", 14L);
        }
        catch (MethodArgumentNotValidException e){
            String message="invalid data for increment ";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void updateSalaryByDepartment_shouldThrowExceptionFor1stParamIncrementWhenZero()
            throws NotFoundException, MethodArgumentNotValidException {
        try {
            salaryService.updateSalaryByDepartment(0L, "rupees", 14L);
        }
        catch (MethodArgumentNotValidException e){
            String message="invalid data for increment ";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void updateSalaryByDepartment_shouldThrowExceptionFor1stParamIncrementWhenNegative()
            throws NotFoundException, MethodArgumentNotValidException {
            try {
                salaryService.updateSalaryByDepartment(-7L, "rupees", 14L);
            }
            catch (MethodArgumentNotValidException e){
                String message="invalid data for increment ";
                assertEquals(message, e.getMessage());
            }
    }

    @Test
    public void updateSalaryByDepartment_shouldThrowExceptionFor2ndParamIncrementWhenNull()
            throws NotFoundException, MethodArgumentNotValidException {
        try {
            salaryService.updateSalaryByDepartment(40L, null, 14L);
        }
        catch (MethodArgumentNotValidException e){
            String message="invalid data for increment, provide currency type ";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void updateSalaryByDepartment_shouldThrowExceptionFor2ndParamCurrencyTypeWhenInValid()
            throws  MethodArgumentNotValidException {

        try {
            Department department = new Department();
            Mockito
                    .when(departmentRepo.findById(14L)).thenReturn(Optional.of(department));
            Mockito.when(currencyConvertorService.getRupeeValue(any(), any())).thenCallRealMethod();
            salaryService.updateSalaryByDepartment(7L, "rups", 14L);
        } catch (NotFoundException e) {
            String message = "Currency type not found ";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void updateSalaryByDepartmentPercentage_shouldThrowExceptionFor1stParamPercentageLessThanNegative100()
            throws NotFoundException {
        try {
            salaryService.updateSalaryByDepartmentPercentage(-101L, 14L);
        } catch (MethodArgumentNotValidException e) {
            String message = "Percent not in range :-100 to 0 or greater than 0";
            assertEquals(message, e.getMessage());

        }
    }

    @Test
    public void updateSalaryByDepartmentPercentage_shouldThrowExceptionFor1stParamPercentageWhenZero()
            throws NotFoundException {
        try {
            salaryService.updateSalaryByDepartmentPercentage(0L, 14L);
        } catch (MethodArgumentNotValidException e) {
            String message = "Percent not in range :-100 to 0 or greater than 0";
            assertEquals(message, e.getMessage());

        }
    }

    @Test
    public void updateSalaryByDepartmentPercentage_shouldThrowExceptionFor1stParamPercentageWhenNull()
            throws NotFoundException{
        try {
            salaryService.updateSalaryByDepartmentPercentage(null, 14L);
        } catch (MethodArgumentNotValidException e) {
            String message = "Percent not in range :-100 to 0 or greater than 0";
            assertEquals(message, e.getMessage());

        }
    }

    @Test
    public void saveSalaryForEmployee_success() throws NotFoundException {
        Employee employee = new Employee();
        employee.setSalaries(new HashSet<Salary>());
        SalaryDto salaryDto = new SalaryDto();
        salaryDto.setAmount(1000);
        salaryDto.setCurrency("EURO");
        Salary salary = new Salary();
        Mockito.when(employeeRepo.findById(any())).thenReturn(Optional.of(employee));
        Mockito.when(currencyConvertorService.getRupeeValue(any(), any())).thenCallRealMethod();
        Mockito.when(salaryRepo.save(any())).thenReturn(salary);
        Mockito.when(employeeRepo.save(any())).thenReturn(new Employee());
        salaryService.saveSalary(1L, salaryDto);
    }

    @Test
    public void updateSalaryByOrganisation() {
    }

    @Test
    public void updateSalaryByOrganisationPercentage() {
    }

    @Test
    public void updateSalaryThroughKafka() {
    }

    @TestConfiguration
    public static class SalaryServiceTestContextConfiguration {

        @Bean
        public SalaryService salaryService() {
            return new SalaryService();
        }
    }

}