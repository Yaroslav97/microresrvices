package app.rest;

import app.model.Equipment;
import app.service.EquipmentService;
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
@Api(value = "EquipmentController", description = "Equipment rest controller")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @ApiOperation(value = "Save equipment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully save"),
            @ApiResponse(code = 500, message = "Error")
    })
    @RequestMapping(value = "/equipment", method = RequestMethod.POST)
    public void saveEquipment(@RequestBody Equipment equipment) {
        log.info("Equipment saved: {}", equipment);
        equipmentService.saveEquipment(equipment);
    }

    @ApiOperation(value = "Get equipment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully obtained"),
            @ApiResponse(code = 500, message = "Error")
    })
    @RequestMapping(value = "/equipment/{id}", method = RequestMethod.GET)
    public Optional<Equipment> getEquipment(@RequestAttribute Long id) {
        log.info("Equipment get: {}", id);
       return equipmentService.getEquipment(id);
    }

}
