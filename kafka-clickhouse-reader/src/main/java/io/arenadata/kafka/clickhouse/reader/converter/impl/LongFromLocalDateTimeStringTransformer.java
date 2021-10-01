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
package io.arenadata.kafka.clickhouse.reader.converter.impl;

import io.arenadata.kafka.clickhouse.reader.converter.transformer.AbstractColumnTransformer;
import io.arenadata.kafka.clickhouse.reader.model.ColumnType;
import io.arenadata.kafka.clickhouse.util.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LongFromLocalDateTimeStringTransformer extends AbstractColumnTransformer<Long, String> {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public Long transformValue(String value) {
        if (value == null) {
            return null;
        }

        LocalDateTime localDateTime = LocalDateTime.parse(value, dateTimeFormatter);
        return DateTimeUtils.toMicros(localDateTime);
    }

    @Override
    public Collection<Class<?>> getTransformClasses() {
        return Collections.singletonList(String.class);
    }

    @Override
    public ColumnType getType() {
        return ColumnType.TIMESTAMP;
    }
}
