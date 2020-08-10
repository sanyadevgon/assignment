package com.company.managementservice;

import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.entity.Organisation;
import com.company.managementservice.model.enums.DesignationType;
import com.company.managementservice.repo.DepartmentRepo;
import com.company.managementservice.repo.EmployeeRepo;
import com.company.managementservice.repo.OrganisationDepartmentRepo;
import com.company.managementservice.repo.OrganisationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ManagementServiceApplication /*implements CommandLineRunner*/{

	public static void main(String[] args) {
		SpringApplication.run(ManagementServiceApplication.class, args);
	}
	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	/*@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	OrganisationDepartmentRepo organisationDepartmentRepo;

	@Autowired
	OrganisationRepo organisationRepo;


	@Override
	public void run(String... args) throws Exception {
		Employee e = new Employee();
		e.setFirstName("sanya");
		e.setIsActive(true);
		e.setDesignationType(DesignationType.CEO);
		e.setPhone("9899756888");
		e.setLastName("devgon");
		employeeRepo.save(e);*/

		/*Department department = new Department();
		department.setName("dep1");
		department.setIsActive(true);
		department.setAddress("delhi");
		Set<Employee> employees= new HashSet<>();
		employees.add(e);
		department.setEmployees(employees);


		Department department2 = new Department();
		department2.setName("dep2");
		department2.setIsActive(true);
		department2.setAddress("Noida");






		Organisation org = new Organisation();
		org.setId(1);
		org.setName("1st org ");
		org.setIsActive(true);
		org.setType("Account");
		Set<Department> departments= new HashSet<>();

		employeeRepo.save(e);
		departmentRepo.save(department);
		departmentRepo.save(department2);
		Department d1= departmentRepo.findById(1L).get();
		Department d2= departmentRepo.findById(2L).get();

		d1.setId(1L);
		d1.setName("dep2");
		d1.setIsActive(true);
		d1.setAddress("Noida");
		d2.setId(2L);
		d2.setName("dep2");
		d2.setIsActive(true);
		d2.setAddress("Noida");

		departments.add(d1);
		departments.add(d2);
		org.setDepartment(departments);
		organisationRepo.save(org);
		employeeRepo.removeEmployee(1L);
*/


// remove mapping of emp with dep and remove mapping of dep with org
		//department.getEmployees().clear();

		//departmentRepo.save(department);

	//	organisationDepartmentRepo.removeDepartment(1L);

		// till here remove mapping

	//}
}
