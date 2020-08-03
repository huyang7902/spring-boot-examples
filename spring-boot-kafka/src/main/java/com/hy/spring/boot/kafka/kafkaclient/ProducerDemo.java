package com.hy.spring.boot.kafka.kafkaclient;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * 生产者 demo
 * @Author yang.hu
 * @Date 2020/7/28 16:36
 */
public class ProducerDemo {

    public static void main(String[] args) {


        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 0:不等待leader返回，1：leader落盘成功，-1(ALL):isr中所有落盘成功
        properties.put(ProducerConfig.ACKS_CONFIG, 1);
        // 序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        // 拦截器
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, Arrays.asList("com.hy.spring.boot.kafka.kafkaclient.ProducerInterceptorDemo"));

        // partition分区策略
//        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.hy.spring.boot.kafka.kafkaclient.MyPartitioner");



        Producer<String, String> producer = new KafkaProducer(properties);

        for (int i = 0; i < 10; i++) {
            long currentTimeMillis = System.currentTimeMillis();
            ProducerRecord producerRecord = new ProducerRecord("test", currentTimeMillis + "-" + i, currentTimeMillis +"");
            Future send = producer.send(producerRecord, (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("seng data: " + producerRecord.toString());
                } else {
                    exception.printStackTrace();
                }
            });
        }

        producer.close();


    }

}
