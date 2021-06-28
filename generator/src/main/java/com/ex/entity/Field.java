package com.ex.entity;

import java.util.Objects;

public class Field{
        private String type;
        private String name;

        public Field() {
        }

        public Field(String type, String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Field field = (Field) o;
            return Objects.equals(type, field.type) &&
                    Objects.equals(name, field.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, name);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }