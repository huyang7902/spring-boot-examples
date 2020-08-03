package com.hy.spring.boot.kafka.kafkaclient;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者拦截器
 *
 * @Author yang.hu
 * @Date 2020/7/29 12:07
 */
public class ProducerInterceptorDemo implements ProducerInterceptor<String, String> {

    private AtomicInteger success = new AtomicInteger();
    private AtomicInteger error = new AtomicInteger();

    /**
     * 该方法封装进 KafkaProducer.send 方法中，即它运行在用户主线程中。Producer 确保在
     * 消息被序列化以及计算分区前调用该方法。用户可以在该方法中对消息做任何操作，但最好
     * 保证不要修改消息所属的 topic 和分区，否则会影响目标分区的计算。
     * @param record
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        // 需要重新 new 一个ProducerRecord，因为ProducerRecord没有 set 方法
        System.out.println("ProducerInterceptorDemo#onSend invoke");
        return record;
    }

    /**
     * 该方法会在消息从 RecordAccumulator 成功发送到 Kafka Broker 之后，或者在发送过程
     * 中失败时调用。并且通常都是在 producer 回调逻辑触发之前。onAcknowledgement 运行在
     * producer 的 IO 线程中，因此不要在该方法中放入很重的逻辑，否则会拖慢 producer 的消息
     * 发送效率。
     * @param metadata
     * @param exception
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

        if (exception == null) {
            success.incrementAndGet();
        } else {
            error.getAndIncrement();
        }
    }

    /**
     * 关闭 interceptor，主要用于执行一些资源清理工作
     */
    @Override
    public void close() {
        System.out.println("成功数量：" + success.get());
        System.out.println("失败数量：" + error.get());

    }

    // 获取配置信息和初始化数据时调用
    @Override
    public void configure(Map<String, ?> configs) {

    }
}
