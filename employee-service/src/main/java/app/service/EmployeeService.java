package app.service;

import app.dao.EmployeeRepository;
import app.dto.Equipment;
import app.feign.EquipmentClient;
import app.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EquipmentClient equipmentClient;

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeByID(Long id) {
        return employeeRepository.findById(id);
    }

    public Equipment getEmployeeEquipmentByID(Long id) {
        return equipmentClient.findByID(id);
    }

    public Iterable<Employee> getEmployeeList() {
        return employeeRepository.findAll();
    }

    public void saveUserEquipment(Equipment equipment) {
        equipmentClient.saveEquipment(equipment);
    }

}
