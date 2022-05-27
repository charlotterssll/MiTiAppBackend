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

import java.util.Objects;

import javax.persistence.MappedSuperclass;

import org.junit.jupiter.api.Test;

/**
 * Test class for the value object superclass {@link AbstractSimpleValueObject}.
 */
public class AbstractSimpleValueObjectTest {

    private static final String A = "a";

    @Test
    public void compareTo() {
        TestStringValueObject a = new TestStringValueObject(A);
        assertThat(a.compareTo(a)).isEqualTo(0);

        TestSecondValueObject b = new TestSecondValueObject(A);
        assertThat(a.compareTo(b)).isEqualTo(0);

        TestStringValueObject c = new TestStringValueObject("b");
        assertThat(a.compareTo(c)).isEqualTo(-1);
        assertThat(c.compareTo(a)).isEqualTo(1);
    }

    @Test
    public void compareToShouldFailForMissingValue() {
        assertThatThrownBy(() -> new TestStringValueObject(A).compareTo(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void hasAnnotationMappedSuperclass() {
        assertThat(AbstractSimpleValueObject.class.getAnnotation(MappedSuperclass.class)).isNotNull();
    }

    @Test
    public void instantiationShouldFailForMissingInput() {
        assertThatThrownBy(() -> new TestStringValueObject(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("value must not be null");
    }

    @Test
    public void instantiationSucceeded() {
        new TestStringValueObject("Abc");
    }

    @Test
    public void testEquals() {
        AbstractSimpleValueObject<String> a = new TestStringValueObject(A);

        assertThat(a.equals(a)).isTrue();
        assertThat(Objects.equals(a, null)).isFalse();

        AbstractSimpleValueObject<String> aChild = new TestStringValueObjectChild(A);
        AbstractSimpleValueObject<String> b = new TestSecondValueObject(A);
        AbstractSimpleValueObject<String> c = new TestStringValueObject(A);
        TestStringValueObject proxy = new TestStringValueObject(A);
        assertThat(a.equals(aChild)).isTrue();
        assertThat(a.equals(b)).isFalse();
        assertThat(a.equals(c)).isTrue();
        assertThat(a.equals(proxy)).isTrue();
        assertThat(proxy.equals(a)).isTrue();
    }

    @Test
    public void testHashCode() {
        TestStringValueObject a = new TestStringValueObject(A);
        assertThat(a.hashCode()).isEqualTo(a.getValue().hashCode());
    }

    @Test
    public void testToString() {
        TestStringValueObject a = new TestStringValueObject(A);
        assertThat(a.toString()).isEqualTo(A);
    }

    public static class TestStringValueObject extends AbstractSimpleValueObject<String> {

        protected TestStringValueObject() {
            // for frameworks
        }

        protected TestStringValueObject(final String value) {
            super(value);
        }
    }

    public static final class TestStringValueObjectChild extends TestStringValueObject {

        private TestStringValueObjectChild(final String value) {
            super(value);
        }
    }

    public static final class TestSecondValueObject extends AbstractSimpleValueObject<String> {

        private TestSecondValueObject(final String value) {
            super(value);
        }
    }

    public static class TestIntegerValueObject extends AbstractSimpleValueObject<Integer> {

        protected TestIntegerValueObject() {
            // for frameworks
        }

        protected TestIntegerValueObject(int value) {
            super(value);
        }
    }

    public static class TestNestedStringValueObject extends AbstractSimpleValueObject<TestStringValueObject> {

        protected TestNestedStringValueObject() {
            // for frameworks
        }

        public TestNestedStringValueObject(TestStringValueObject value) {
            super(value);
        }
    }

    public static class TestNestedIntegerValueObject extends AbstractSimpleValueObject<TestIntegerValueObject> {

        protected TestNestedIntegerValueObject() {
            // for frameworks
        }

        public TestNestedIntegerValueObject(TestIntegerValueObject value) {
            super(value);
        }
    }
}
