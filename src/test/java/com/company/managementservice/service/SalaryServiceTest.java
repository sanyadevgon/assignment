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
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

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
        Assertions.assertEquals(Long.valueOf(current.getAmount()), 1000L);
    }

    @Test(expected = NotFoundException.class)
    public void getEmployeeCurrentSalary_ShouldThrowExceptionWhenEmployeeIdNotFound_() throws NotFoundException {

        Employee employee = new Employee();
        Set salaries = new HashSet<Salary>();
        employee.setSalaries(salaries);

        Mockito.when(employeeRepo.findById(1l)).thenReturn(Optional.of(employee));

        SalaryDto current = salaryService.getEmployeeCurrentSalary(1L);

    }

    @Test(expected = MethodArgumentNotValidException.class)
    public void updateSalaryByDepartment_ShouldThrowExceptionForNullIncrement()
            throws NotFoundException, MethodArgumentNotValidException {
        salaryService.updateSalaryByDepartment(null, "rupees", 14L);
    }

    @Test(expected = MethodArgumentNotValidException.class)
    public void updateSalaryByDepartment_ShouldThrowExceptionForNegavtiveIncrement()
            throws NotFoundException, MethodArgumentNotValidException {
        salaryService.updateSalaryByDepartment(-7L, "rupees", 14L);
    }

    @Test(expected = NotFoundException.class)
    public void updateSalaryByDepartment_ShouldThrowExceptionForInValidCurrencyType()
            throws NotFoundException, MethodArgumentNotValidException {
        Department department = new Department();
        Mockito
                .when(departmentRepo.findById(14L)).thenReturn(Optional.of(department));
        Mockito.when(currencyConvertorService.getRupeeValue(any(), any())).thenCallRealMethod();

        salaryService.updateSalaryByDepartment(7L, "rups", 14L);
    }

    @Test(expected = MethodArgumentNotValidException.class)
    public void updateSalaryByDepartmentPercentage_ShouldThrowExceptionFor1stParamLessThanNegative100()
            throws NotFoundException, MethodArgumentNotValidException {
        salaryService.updateSalaryByDepartmentPercentage(-101L, 14L);
    }

    @Test(expected = MethodArgumentNotValidException.class)
    public void updateSalaryByDepartmentPercentage_ShouldThrowExceptionFor1stParamWhenZero()
            throws NotFoundException, MethodArgumentNotValidException {
        salaryService.updateSalaryByDepartmentPercentage(0L, 14L);
    }

    @Test(expected = MethodArgumentNotValidException.class)
    public void updateSalaryByDepartmentPercentage_ShouldThrowExceptionFor1stParamWhenNull()
            throws NotFoundException, MethodArgumentNotValidException {
        salaryService.updateSalaryByDepartmentPercentage(null, 14L);
    }

    @Test
    public void saveSalary() throws NotFoundException {
        Employee employee = new Employee();
        employee.setSalaries(new HashSet<Salary>());
        SalaryDto salaryDto = new SalaryDto();
        Mockito.when(employeeRepo.findById(any())).thenReturn(Optional.of(employee));
        Mockito.when(salaryRepo.save(any())).thenReturn(new Salary());
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