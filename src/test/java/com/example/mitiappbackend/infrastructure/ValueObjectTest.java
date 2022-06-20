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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class ValueObjectTest {

    private static final String V_ONE = "v1";
    private static final String V_TWO = "v2";

    @Test
    public void testEquals() {
        TestMultiValueObject valueObject = new TestMultiValueObject(
            new TestFirstSimpleValueObject(V_ONE),
            new TestSecondSimpleValueObject(V_TWO));
        TestMultiValueObject proxy = new TestMultiValueObject() {
            @Override
            protected Object[] values() {
                return new Object[]{new TestFirstSimpleValueObject(V_ONE), new TestSecondSimpleValueObject(V_TWO)};
            }
        };

        assertThat(valueObject.equals(valueObject)).isTrue();
        assertThat(valueObject.equals(V_ONE)).isFalse();
        assertThat(valueObject.equals(null)).isFalse();
        assertThat(valueObject.equals(proxy)).isTrue();
        assertThat(proxy.equals(valueObject)).isTrue();
        assertThat(valueObject.equals(
            new TestMultiValueObject(new TestFirstSimpleValueObject(V_ONE), new TestSecondSimpleValueObject(V_TWO))))
                .isTrue();
        assertThat(valueObject.equals(
            new TestMultiValueObject(new TestFirstSimpleValueObject(V_TWO), new TestSecondSimpleValueObject(V_ONE))))
                .isFalse();
        assertThat(valueObject.equals(new TestFirstSimpleValueObject(V_ONE))).isFalse();
    }

    @Test
    public void testHashCode() {
        TestMultiValueObject valueObject = new TestMultiValueObject(
            new TestFirstSimpleValueObject(V_ONE),
            new TestSecondSimpleValueObject(V_TWO));

        assertThat(valueObject.hashCode())
            .isEqualTo(
                new TestMultiValueObject(
                    new TestFirstSimpleValueObject(V_ONE),
                    new TestSecondSimpleValueObject(V_TWO)).hashCode());

        assertThat(valueObject.hashCode())
            .isNotEqualTo(
                new TestMultiValueObject(
                    new TestFirstSimpleValueObject(V_TWO),
                    new TestSecondSimpleValueObject(V_ONE)).hashCode());

        assertThatThrownBy(() -> new EmptyValueObject().hashCode()).isInstanceOf(IllegalStateException.class);

        assertThatThrownBy(() -> new IllegalValueObject().hashCode()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testToString() {
        assertThat(new TestMultiValueObject(new TestFirstSimpleValueObject(V_ONE), new TestSecondSimpleValueObject(V_TWO))
                .toString()).contains("TestMultiValueObject[").contains("first=v1").contains("second=v2").endsWith("]");
    }

    public static class TestFirstSimpleValueObject extends AbstractSimpleValueObject<String> {

        public TestFirstSimpleValueObject(final String value) {
            super(value);
        }
    }

    public static class TestSecondSimpleValueObject extends AbstractSimpleValueObject<String> {

        public TestSecondSimpleValueObject(final String value) {
            super(value);
        }
    }

    public static class TestMultiValueObject extends AbstractValueObject {

        private TestFirstSimpleValueObject first;

        private TestSecondSimpleValueObject second;

        protected TestMultiValueObject() {
            // for JPA
        }

        public TestMultiValueObject(TestFirstSimpleValueObject aFirst, TestSecondSimpleValueObject aSecond) {
            first = aFirst;
            second = aSecond;
        }

        public TestMultiValueObject(Object first, TestSecondSimpleValueObject second) {
            this(first, second, true);
        }

        public TestMultiValueObject(TestFirstSimpleValueObject first, Object second) {
            this(first, new TestSecondSimpleValueObject(second.toString()), false);
        }

        public TestMultiValueObject(Object first, TestSecondSimpleValueObject second, boolean convert) {
            this(convert ? new TestFirstSimpleValueObject(first.toString()) : (TestFirstSimpleValueObject)first, second);
        }

        @Override
        protected Object[] values() {
            return new Object[]{first, second};
        }
    }

    public static class SecondValueObject extends AbstractValueObject {

        @Override
        protected Object[] values() {
            return new Object[]{"second"};
        }
    }

    public static class EmptyValueObject extends AbstractValueObject {

        @Override
        protected Object[] values() {
            return new Object[]{};
        }
    }

    public static class IllegalValueObject extends AbstractValueObject {

        @Override
        protected Object[] values() {
            return null;
        }
    }
}
