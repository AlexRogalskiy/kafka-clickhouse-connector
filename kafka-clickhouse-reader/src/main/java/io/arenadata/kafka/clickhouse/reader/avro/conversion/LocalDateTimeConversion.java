/*
 * Copyright © 2021 Kafka Clickhouse Reader
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
package io.arenadata.kafka.clickhouse.reader.avro.conversion;

import io.arenadata.kafka.clickhouse.reader.avro.type.LocalDateTimeLogicalType;
import org.apache.avro.Conversion;
import org.apache.avro.LogicalType;
import org.apache.avro.Schema;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeConversion extends Conversion<LocalDateTime> {

  @Override
  public Class<LocalDateTime> getConvertedType() {
    return LocalDateTime.class;
  }

  @Override
  public String getLogicalTypeName() {
    return LocalDateTimeLogicalType.INSTANCE.getName();
  }

  @Override
  public Schema getRecommendedSchema() {
    return LocalDateTimeLogicalType.INSTANCE.addToSchema(Schema.create(Schema.Type.LONG));
  }

  @Override
  public Long toLong(LocalDateTime value, Schema schema, LogicalType type) {
    return value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  @Override
  public LocalDateTime fromLong(Long value, Schema schema, LogicalType type) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
  }
}
