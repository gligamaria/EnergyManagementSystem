package ro.tuc.ds2020.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    Queue queue(){return new Queue("fericire2", true);}

    @Bean
    DirectExchange exchange(){ return new DirectExchange("testexchange");}

    @Bean
    Binding binding(Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("fericire2");
    }

    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri("amqps://axbccuwn:pqNzPJmo-u7eEVf_XiQtQgRB3bTmX_of@cow.rmq2.cloudamqp.com/axbccuwn");
        connectionFactory.setUsername("axbccuwn");
        connectionFactory.setPassword("pqNzPJmo-u7eEVf_XiQtQgRB3bTmX_of");
        connectionFactory.setPort(5671);
        connectionFactory.setVirtualHost("axbccuwn");
        return connectionFactory;

    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("Message sent failed: " + cause);
            }
        });
        return template;

    }
}
