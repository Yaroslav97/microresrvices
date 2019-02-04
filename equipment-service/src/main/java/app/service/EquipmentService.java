package app.service;

import app.dao.EquipmentRepository;
import app.model.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public void saveEquipment(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    public Optional<Equipment> getEquipment(Long id) {
        return equipmentRepository.findById(id);
    }

}
