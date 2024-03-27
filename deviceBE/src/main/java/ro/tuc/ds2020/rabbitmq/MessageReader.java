package ro.tuc.ds2020.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReader {

    MessageSender messageSender;

    public MessageReader (MessageSender messageSender){
        this.messageSender = messageSender;
    }

    @RabbitListener(queues = "fericire2")
    public void listenBack(String deviceId){
        System.out.println(deviceId);
        messageSender.sendConsumption(deviceId);
    }

}
