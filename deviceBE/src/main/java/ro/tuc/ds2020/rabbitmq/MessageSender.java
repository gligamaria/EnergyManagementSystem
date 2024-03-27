package ro.tuc.ds2020.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.services.DeviceService;

import java.util.UUID;

@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DeviceService deviceService;

    public void sendConsumption(String deviceId) {
        UUID uuidId = UUID.fromString(deviceId);
        Device device = this.deviceService.findDeviceById(uuidId);
        System.out.println(device.getConsumption());
        rabbitTemplate.convertAndSend("fericire2", device.getConsumption());
    }
}
