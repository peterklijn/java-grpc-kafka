package com.peterklijn.service.server.producers;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import io.dropwizard.lifecycle.Managed;

public class KafkaProducerManager implements Managed {
  public final Properties properties;
  private KafkaProducer producer;

  public KafkaProducerManager(final String kafkaBootstrapServers) {
    final HashMap<String, Object> props = new HashMap<>();
    props.put("bootstrap.servers", kafkaBootstrapServers);
    props.put("acks", "all");
    props.put("retries", 3);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    this.properties = new Properties();
    this.properties.putAll(props);
  }

  @Override
  public void start() throws Exception {
    producer = new KafkaProducer<>(properties);
  }

  @Override
  public void stop() throws Exception {
    producer.close();
  }

  public CompletableFuture<RecordMetadata> send(final String topic, final String key, final String message) {
    return sendHelper(new ProducerRecord<>(topic, key, message));
  }

  private CompletableFuture<RecordMetadata> sendHelper(final ProducerRecord<String, String> record) {
    final CompletableFuture<RecordMetadata> result = new CompletableFuture<>();
    producer.send(record, (meta, exception) -> {
      if (exception != null) {
        result.completeExceptionally(exception);
      } else {
        result.complete(meta);
      }
    });

    return result;
  }
}
