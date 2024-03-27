package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceController(DeviceService deviceService, DeviceRepository deviceRepository) {
        this.deviceService = deviceService;
        this.deviceRepository = deviceRepository;
    }

//    @GetMapping()
//    public ResponseEntity<List<DeviceDTO>> getDevices() {
//        List<DeviceDTO> dtos = deviceService.findDevices();
//        return new ResponseEntity<>(dtos, HttpStatus.OK);
//    }

    @GetMapping( value = "/getAll")
    @ResponseBody
    public List<Device> getDevices() {
        return deviceService.findDevices();
    }

    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable("id") UUID deviceId) {
        Device device = deviceService.findDeviceById(deviceId);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @GetMapping( "/getFreeIds")
    @ResponseBody
    public List<String> getFreeIds() {
        return deviceService.getFreeIds();
    }

    @GetMapping( "/getFreeDevices")
    @ResponseBody
    public List<Device> getFreeDevices() {
        return deviceService.getFreeDevices();
    }

    @GetMapping( value = "/getByUserId/{id}")
    @ResponseBody
    public List<Device> getDevices(@PathVariable("id") UUID userId) {
        List<Device> allDevices = deviceService.findDevices();
        List<Device> filteredDevices = new ArrayList<>();
        for(Device device:allDevices){
            if(device.getUserId().equals(userId))
                filteredDevices.add(device);
        }
        return filteredDevices;
    }

    @GetMapping( value = "/getDevicesByUser/{id}")
    @ResponseBody
    public List<Device> getDevicesByUser(@PathVariable("id") UUID userId) {
        return deviceService.getDevicesByUser(userId);
    }

    @GetMapping( value = "/getIdsByUserId/{id}")
    @ResponseBody
    public List<String> getIdsByUser(@PathVariable("id") UUID userId) {
        return deviceService.getIdsByUser(userId);
    }

    @PostMapping(value = "/insertDevice")
    public ResponseEntity<UUID> insertDevice(@Valid @RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        UUID deviceID = deviceService.insert(deviceDetailsDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @PutMapping(value = "/updateDeviceWithUser/{userId}")
    public List<Device> updateDeviceWithUser(@PathVariable("userId") UUID userID){
        List<Device> allDevice = deviceService.findDevices();
        List<Device> modifiedDevices = new ArrayList<>();
        for (Device device:allDevice){
            if(device.getUserId() != null && device.getUserId() == userID){
                device.setUserId(null);
                modifiedDevices.add(device);
                deviceRepository.save(device);
            }
        }
        return modifiedDevices;
    }

    @PutMapping(value = "/addUserToDevice/{deviceId}/{userId}")
    public ResponseEntity<Device> addUserToDevice(@PathVariable ("deviceId") UUID deviceId, @PathVariable ("userId") UUID userId){

        Device device = deviceService.findDeviceById(deviceId);
        device.setUserId(userId);
        deviceRepository.save(device);

        System.out.println(userId);
        System.out.println(deviceId);

        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PutMapping(value = "/updateDevice/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable("id") UUID deviceID,
                                                  @RequestBody DeviceDetailsDTO deviceDetailsDTO) {

        DeviceDetailsDTO dto = deviceService.findDeviceDetailedById(deviceID);
        Device newDevice = DeviceBuilder.toEntity(dto);

        if(deviceDetailsDTO.getUserId() != null){
            newDevice.setUserId(deviceDetailsDTO.getUserId());
        }
        if(deviceDetailsDTO.getAddress() != null){
            newDevice.setAddress(deviceDetailsDTO.getAddress());
        }
        if(deviceDetailsDTO.getDescription() != null){
            newDevice.setDescription(deviceDetailsDTO.getDescription());
        }
        if(deviceDetailsDTO.getConsumption() != null){
            newDevice.setConsumption(deviceDetailsDTO.getConsumption());
        }
        newDevice.setId(deviceID);
        deviceRepository.save(newDevice);

        return new ResponseEntity<>(newDevice, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") UUID deviceID){

        if(deviceService.findDeviceById(deviceID) != null)
        {
            return new ResponseEntity<>(deviceService.deleteById(deviceID), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
