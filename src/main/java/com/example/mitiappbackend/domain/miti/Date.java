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
package com.example.mitiappbackend.domain.miti;

import static org.apache.commons.lang3.Validate.notBlank;

import javax.persistence.Embeddable;

import com.example.mitiappbackend.infrastructure.AbstractSimpleValueObject;

@Embeddable
public class Date extends AbstractSimpleValueObject<String> {

    public Date(String date) {
        super(date);
    }

    protected Date() {
    }

    @Override
    protected String validateAndNormalize(String date) {
        //isTrue(date.matches("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$"), "date must only contain numbers in 24h time format");
        return notBlank(date);
    }
}
