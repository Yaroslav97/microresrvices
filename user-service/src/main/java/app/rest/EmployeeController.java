package app.rest;

import app.model.Employee;
import app.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@Api(value = "EmployeeController", description = "Employee rest controller")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "Save employee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully save"),
            @ApiResponse(code = 500, message = "Error")
    })
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public void saveEmployee(@RequestBody Employee employee) {
        log.info("Employee saved: {}", employee);
        employeeService.saveEmployee(employee);
    }

    @ApiOperation(value = "Get employee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully obtained"),
            @ApiResponse(code = 500, message = "Error")
    })
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public Optional<Employee> getEmployeeByid(@RequestAttribute() Long id) {
        log.info("Get employee: {}", id);
        return employeeService.getEmployeeByID(id);
    }

    @ApiOperation(value = "Get employee list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully obtained"),
            @ApiResponse(code = 500, message = "Error")
    })
    @RequestMapping(value = "/employee/list", method = RequestMethod.GET)
    public Iterable<Employee> getAllEmployee() {
        log.info("Get all employee");
        return employeeService.getEmployeeList();
    }

}
