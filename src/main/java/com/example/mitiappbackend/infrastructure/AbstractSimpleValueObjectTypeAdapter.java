/*
 * Copyright \(C\) open knowledge GmbH\.
 *
 * Licensed under the Apache License, Version 2\.0 \(the "License"\);
 * you may not use this file except in compliance with the License\.
 * You may obtain a copy of the License at
 *
 *     http://www\.apache\.org/licenses/LICENSE-2\.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied\.
 * See the License for the specific language governing permissions and
 * limitations under the License\.
 */
package com.example.mitiappbackend.infrastructure;

/*import javax.json.bind.adapter.JsonbAdapter;

public class AbstractSimpleValueObjectTypeAdapter<V, P> implements JsonbAdapter<V, P> {

    private ObjectBuilder<V> valueObjectBuilder;
    private ObjectBuilder<P> primitiveBuilder;

    protected AbstractSimpleValueObjectTypeAdapter() {
        valueObjectBuilder = ObjectBuilder.<V>fromGenericType(getClass(), AbstractSimpleValueObjectTypeAdapter.class, 0);
        primitiveBuilder = ObjectBuilder.<P>fromGenericType(getClass(), AbstractSimpleValueObjectTypeAdapter.class, 1);
    }

    @Override
    public P adaptToJson(V valueObject) throws Exception {
        return primitiveBuilder.withParameter(valueObject.toString()).build();
    }

    @Override
    public V adaptFromJson(P jsonValue) throws Exception {
        return valueObjectBuilder.withValue(jsonValue).build();
    }
}*/
