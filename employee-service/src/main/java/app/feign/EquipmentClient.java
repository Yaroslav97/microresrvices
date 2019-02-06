package app.feign;

import app.dto.Equipment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "equipment-service")
public interface EquipmentClient {

    @GetMapping("/equipment")
    Equipment findByID(@RequestParam("id") Long id);

    @PostMapping("/equipment")
    void saveEquipment(@RequestBody Equipment equipment);

}
