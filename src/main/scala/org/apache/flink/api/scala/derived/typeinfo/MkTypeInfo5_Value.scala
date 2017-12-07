/*
 * Copyright 2017 Georgi Krastev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.flink
package api.scala.derived.typeinfo

import api.java.typeutils.ValueTypeInfo
import types.Value

import scala.reflect.ClassTag

/** `TypeInformation` instances for `Value` types. */
private[typeinfo] abstract class MkTypeInfo5_Value extends MkTypeInfo6_Singleton {

  /** Creates `TypeInformation` for the `Value` type `V`. */
  implicit def mkValueTypeInfo[V <: Value](implicit tag: ClassTag[V]): MkTypeInfo[V] =
    this(new ValueTypeInfo(tag.runtimeClass.asInstanceOf[Class[V]]))
}