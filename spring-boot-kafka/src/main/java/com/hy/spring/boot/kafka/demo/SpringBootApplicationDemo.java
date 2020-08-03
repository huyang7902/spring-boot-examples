package com.hy.spring.boot.kafka.demo;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yang.hu
 * @Date 2020/8/3 16:56
 */
@SpringBootApplication
@RestController
public class SpringBootApplicationDemo {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationDemo.class, args);
    }

    @Resource
    KafkaTemplate<String, String> kafkaTemplate;

    public static String topic = "test";

    /**
     * 消息的发送
     * @param msg
     * @return
     */
    @RequestMapping("/send/{msg}")
    public String sendMsg(@PathVariable String msg) {

        System.out.println("sendMsg: " + msg);

        kafkaTemplate.send(topic, msg);
        kafkaTemplate.send(topic, msg + "-" + System.currentTimeMillis(), msg);

        ProducerRecord producerRecord = new ProducerRecord(topic, msg + "-" + System.currentTimeMillis(), msg);
        kafkaTemplate.send(producerRecord);


        return "success";
    }

    /**
     * 消息的发送,事务支持
     * @param msg
     * @return
     */
    @RequestMapping("/sendTx/{msg}")
    public String sendMsgTx(@PathVariable String msg) {

        System.out.println("sendMsg: " + msg);

        // 事务支持, ack必须设置为-1,
        // 设置 spring.kafka.producer.transaction-id-prefix 表明开始开启事务，此时用普通的send方法会提示错误
        kafkaTemplate.executeInTransaction(operations -> {
            kafkaTemplate.send(topic, msg + "-tx1", msg + "-tx1");

            if ("error".equals(msg)) {
                throw new RuntimeException("msg is error");
            }
            kafkaTemplate.send(topic, msg + "-tx2", msg + "-tx2");

            return true;
        });


        return "success";
    }

    /**
     * 消息的发送,事务支持，注解支持
     * @param msg
     * @return
     */
    @RequestMapping("/sendTx2/{msg}")
    @Transactional(rollbackFor = Throwable.class)
    public String sendMsgTx2(@PathVariable String msg) {

        System.out.println("sendMsg: " + msg);

        kafkaTemplate.send(topic, msg + "-tx1", msg + "-tx1");

        if ("error".equals(msg)) {
            throw new RuntimeException("msg is error");
        }
        kafkaTemplate.send(topic, msg + "-tx2", msg + "-tx2");
        return "success";
    }

    @KafkaListener(topics = {"test"}, groupId = "SpringBootApplicationDemo")
    public void receiveMsg(String msg){

        System.out.println("receiveMsg: " + msg);
    }

}
