package app.rest;

import app.model.User;
import app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(value = "ServiceInstanceRestController", description = "Service instance rest controller")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Save user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully save"),
            @ApiResponse(code = 500, message = "Error")
    })
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void saveUser(@RequestBody User user) {
        log.info("User saved: {}", user);
        userService.saveUser(user);
    }

}
