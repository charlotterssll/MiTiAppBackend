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
package com.example.mitiappbackend.domain.employee;

import static org.apache.commons.lang3.Validate.notBlank;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Embeddable;

import com.example.mitiappbackend.domain.employee.FirstName.Adapter;
import com.example.mitiappbackend.infrastructure.AbstractSimpleValueObject;
import com.example.mitiappbackend.infrastructure.AbstractSimpleValueObjectTypeAdapter;

@Embeddable
@JsonbTypeAdapter(Adapter.class)
public class FirstName extends AbstractSimpleValueObject<String> {

    public FirstName(String firstName) {
        super(firstName);
    }

    protected FirstName() {
    }

    @Override
    protected String validateAndNormalize(String firstName) {
        return notBlank(firstName);
    }

    public static class Adapter extends AbstractSimpleValueObjectTypeAdapter<FirstName, String> {
    }
}
