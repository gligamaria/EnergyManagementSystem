package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> findDevices() {
//        List<Device> deviceList = deviceRepository.findAll();
//        return deviceList.stream()
//                .map(DeviceBuilder::toDeviceDTO)
//                .collect(Collectors.toList());
        return deviceRepository.findAll();

    }

    public Device findDeviceById(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return deviceOptional.get();
    }

    public DeviceDetailsDTO findDeviceDetailedById(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDetailDTO(deviceOptional.get());
    }

    public UUID insert(DeviceDetailsDTO deviceDetailsDTO) {
        Device device = DeviceBuilder.toEntity(deviceDetailsDTO);
        device = deviceRepository.save(device);
        LOGGER.debug("Person with id {} was inserted in db", device.getId());
        return device.getId();
    }

    public String deleteById(UUID deviceID){
        try{
            deviceRepository.deleteById(deviceID);
            return "Success";
        }
        catch (Exception e){
            e.printStackTrace();
            return "Failed";
        }
    }

    public List<Device> getFreeDevices() {
        List<Device> freeDevices = new ArrayList<>();
        List<Device> devices = this.findDevices();
        for(Device device:devices){
            if(device.getUserId() != null)
                freeDevices.add(device);
        }
        return freeDevices;
    }

    public List<String> getFreeIds() {
        List<String> ids = new ArrayList<>();
        List<Device> devices = this.findDevices();
        for(Device device:devices){
            if(device.getUserId() == null)
                ids.add(device.getId().toString());
        }
        return ids;
    }

    public List<String> getIdsByUser(UUID userID) {
        List<String> ids = new ArrayList<>();
        List<Device> devices = this.findDevices();
        for(Device device:devices){
            if(device.getUserId() != null && device.getUserId().equals(userID))
                ids.add(device.getId().toString());
        }
        return ids;
    }

    public List<Device> getDevicesByUser(UUID userID) {
        List<Device> userDevices = new ArrayList<>();
        List<Device> devices = this.findDevices();
        for(Device device:devices){
            if(device.getUserId() != null && device.getUserId().equals(userID))
                userDevices.add(device);
        }
        return userDevices;
    }
}
