package com.hy.spring.boot.kafka.kafkaclient;

import org.apache.kafka.clients.consumer.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * 消费者 demo
 *
 * @Author yang.hu
 * @Date 2020/7/28 16:46
 */
public class ConsumerDemo {

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-1");
        // 从最开始消费，默认值
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 从最后开始消费
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        // 开启自动提交offset，可以关闭手动提交
        // 程序刚启动时，会从kafka中读取offset中的值，然后保存在内存中，会每次提交到kafka中
        // 自动提交：缺点，有可能重复消费数据或丢失数据，当自动提交前，服务挂了，重启，读取上一次提交的offset，时间不可控
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // 自动提交间隔毫秒数，默认5s
//        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);

        // 反序列化设置
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        Consumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // 订阅主题，可以自定义offset存储，对数据一致性要求较高时，可以使用mysql事务机制
//        consumer.subscribe(Arrays.asList("test"));
        consumer.subscribe(Arrays.asList("test"));

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));

            if (consumerRecords == null || consumerRecords.isEmpty()) {
                System.out.println("consumerRecords is empty");
                Thread.sleep(200L);
            } else {
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    System.out.println("receive msg: " + consumerRecord.toString());
                }

                // 手动提交offset，推荐，不会丢数据，但是可能重复消费数据，
                consumer.commitAsync((offsets, exception) -> {
                    if (exception == null) {
                        System.out.println("commitAsync: " + offsets);
                    } else {
                        exception.printStackTrace();
                    }
                });

            }

        }

    }

}
