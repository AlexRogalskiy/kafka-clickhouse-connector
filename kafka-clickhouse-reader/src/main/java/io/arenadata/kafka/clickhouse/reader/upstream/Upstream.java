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
package io.arenadata.kafka.clickhouse.reader.upstream;

import io.arenadata.kafka.clickhouse.reader.model.QueryRequest;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface Upstream<Item> {
  void push(QueryRequest queryRequest, Item item, Handler<AsyncResult<Void>> handler);
  void close();
}
