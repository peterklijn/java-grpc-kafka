package com.peterklijn.service.client.consumers;

import java.util.HashMap;
import java.util.Properties;
import java.util.stream.StreamSupport;

import com.google.common.collect.ImmutableList;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ClientKafkaConsumer implements Runnable {
  private final KafkaConsumer kafkaConsumer;

  public ClientKafkaConsumer(final String kafkaBootStrapServers, final String groupId, final String topic) {
    final HashMap<String, Object> props = new HashMap<>();
    props.put("bootstrap.servers", kafkaBootStrapServers);
    props.put("group.id", groupId);
    props.put("enable.auto.commit", "false");
    props.put("auto.offset.reset", "earliest");
    props.put("max.poll.records", "1000");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

    final Properties properties = new Properties();
    properties.putAll(props);

    kafkaConsumer = new KafkaConsumer(properties);
    kafkaConsumer.subscribe(ImmutableList.of(topic));
  }

  @Override
  public void run() {
    System.out.println("Starting kafka consumer");
    while (!Thread.interrupted()) {
      final ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
      if (!records.isEmpty()) {
        processRecords(records);
      }
    }

    System.out.println("Closing kafka consumer");
    kafkaConsumer.close();
  }

  private void processRecords(final ConsumerRecords<String, String> records) {
    System.out.println("RECORDS! ");
    StreamSupport.stream(records.spliterator(), false).forEach(record -> {
      System.out.println("RECORD: " + record.key() + " value: " + record.value());
    });
  }


}
