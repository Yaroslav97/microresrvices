package app.service;

import app.dao.EmployeeRepository;
import app.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeByID(Long id) {
        return employeeRepository.findById(id);
    }

    public Iterable<Employee> getEmployeeList() {
        return employeeRepository.findAll();
    }

}
