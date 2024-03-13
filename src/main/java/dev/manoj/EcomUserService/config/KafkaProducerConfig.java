package dev.manoj.EcomUserService.config;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerConfig {

    private KafkaTemplate<String ,String>kafkaTemplate;

    public KafkaProducerConfig(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void sendMessage(String topic,String message){
        kafkaTemplate.send(topic,message);
    }
}
