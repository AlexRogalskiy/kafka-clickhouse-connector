/*
 * Copyright © 2021 Arenadata Software LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.arenadata.kafka.clickhouse.reader.service.impl;

import io.arenadata.kafka.clickhouse.reader.configuration.properties.KafkaProperties;
import io.arenadata.kafka.clickhouse.reader.factory.KafkaProducerFactory;
import io.arenadata.kafka.clickhouse.reader.factory.VertxKafkaProducerFactory;
import io.arenadata.kafka.clickhouse.reader.service.KafkaProducerProvider;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducer;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KafkaProducerProviderImpl implements KafkaProducerProvider {

    private final Map<String, KafkaProducer<byte[], byte[]>> kafkaProducerMap = new ConcurrentHashMap<>();
    private final KafkaProperties kafkaProperties;
    private final Vertx vertx;

    @Autowired
    public KafkaProducerProviderImpl(KafkaProperties kafkaProperties, Vertx vertx) {
        this.vertx = vertx;
        this.kafkaProperties = kafkaProperties;
    }

    @Override
    public KafkaProducer<byte[], byte[]> getOrCreate(String brokersList) {
        final KafkaProducer<byte[], byte[]> kafkaProducer = kafkaProducerMap.get(brokersList);
        if (kafkaProducer == null) {
            kafkaProducerMap.put(brokersList, getKafkaProducerFactory(kafkaProperties, brokersList)
                    .create(kafkaProperties.getProducer().getProperty()));
        }
        return kafkaProducerMap.get(brokersList);
    }

    private KafkaProducerFactory<byte[], byte[]> getKafkaProducerFactory(KafkaProperties kafkaProperties,
                                                                         String brokersList) {
        var props = new HashMap<>(kafkaProperties.getProducer().getProperty());
        props.put("bootstrap.servers", brokersList);
        return new VertxKafkaProducerFactory<>(this.vertx, props);
    }
}
