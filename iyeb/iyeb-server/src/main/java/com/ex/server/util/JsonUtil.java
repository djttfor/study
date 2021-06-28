package com.ex.server.util;

import com.ex.server.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: byteblogs
 * @date: 2019/8/3 14:57
 */
public class JsonUtil {

    /**
     * 将Java对象转JSON 字符串
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        return JsonMapper.toJsonString(object);
    }

    /**
     * 将JSON 字符串转Java 对象
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        return JsonMapper.toJavaObject(jsonString, clazz);
    }

    /**
     * 将JSON 数组字符串转Java 对象集合
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseList(String jsonString, Class<T> clazz) {
        JavaType javaType = JsonMapper.getCollectionType(ArrayList.class, clazz);
        return (List<T>) JsonMapper.toJavaObject(jsonString, javaType);
    }

    /**
     * 将JSON字符串转Map 对象
     * @param jsonString
     * @return
     */
    public static <K, V> HashMap parseHashMap(String jsonString) {
        return JsonMapper.toJavaObject(jsonString, HashMap.class);
    }

    /**
     * 将JSON字符串转Map 对象
     * @param jsonString
     * @return
     */
    public static <T> Map<String, T> parseHashMap(String jsonString, Class<T> clazz) {
        return (Map<String, T>) JsonMapper.toJavaObject(jsonString, new TypeReference<HashMap<String, T>>() {});
    }

    /**
     * 将JSON字符串转JSON 对象
     * @param jsonString
     * @return
     */
    public static JsonNode toJson(String jsonString) {
        return JsonMapper.toJson(jsonString);
    }

    /**
     * 将JSON字符串转JSON 对象
     * @param object
     * @return
     */
    public static JsonNode toJson(Object object) {
        return JsonMapper.toJson(object);
    }

    /**
     * 将JSON字符串转JSON 对象
     * @param jsonBytes
     * @return
     */
    public static JsonNode toJson(byte[] jsonBytes) {
        return JsonMapper.toJson(jsonBytes);
    }

    /**
     * 内部类，处理Json
     */
    private static class JsonMapper {

        private static final ObjectMapper objectMapper = jacksonObjectMapper();

        /**
         * 获取对象类型
         * @param collectionClass
         * @param elementClasses
         * @return
         */
        private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
            return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        }

        /**
         * JAVA 对象转Json 字符串
         * @param object
         * @return
         */
        private static String toJsonString(Object object) {
            try {
                return objectMapper.writeValueAsString(object);
            } catch (Exception e) {
                throw new BusinessException("对象转Json失败！", e);
            }
        }

        /**
         * Json 字符串转JAVA 对象
         * @param jsonString
         * @param clazz
         * @param <T>
         * @return
         */
        private static <T> T toJavaObject(String jsonString, Class<T> clazz) {
            try {
                return objectMapper.readValue(jsonString, clazz);
            } catch (Exception e) {
                throw new BusinessException("Json转对象失败！", e);
            }
        }

        /**
         * Json 字符串转JAVA 对象
         * @param jsonString
         * @param typeReference
         * @return
         */
        private static Object toJavaObject(String jsonString, TypeReference typeReference) {
            try {
                return objectMapper.readValue(jsonString, typeReference);
            } catch (Exception e) {
                throw new BusinessException("Json转对象失败！", e);
            }
        }

        /**
         * Json 字符串转JAVA 对象
         * @param jsonString
         * @param javaType
         * @return
         */
        private static Object toJavaObject(String jsonString, JavaType javaType) {
            try {
                return objectMapper.readValue(jsonString, javaType);
            } catch (Exception e) {
                throw new BusinessException("Json转对象失败！", e);
            }
        }

        /**
         * Json 字符串转JSON 对象
         * @param jsonString
         * @return
         */
        private static JsonNode toJson(String jsonString) {
            try {
                return objectMapper.readTree(jsonString);
            } catch (Exception e) {
                throw new BusinessException("Json转对象失败！", e);
            }
        }

        /**
         * Json 字符串转JSON 对象
         * @param jsonBytes
         * @return
         */
        private static JsonNode toJson(byte[] jsonBytes) {
            try {
                return objectMapper.readTree(jsonBytes);
            } catch (Exception e) {
                throw new BusinessException("Json转对象失败！", e);
            }
        }

        /**
         * Json 字符串转JSON 对象
         * @param object
         * @return
         */
        private static JsonNode toJson(Object object) {
            try {
                return objectMapper.valueToTree(object);
            } catch (Exception e) {
                throw new BusinessException("Json转对象失败！", e);
            }
        }
    }
    public static ObjectMapper jacksonObjectMapper() {

        // 初始化全局Jackson 序列化工具
        ObjectMapper objectMapper = new ObjectMapper();

        // 忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 忽略无法序列化的属性
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 序列化是忽略空值字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 初始化Java8 时间序列化器
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeNumber(String.valueOf(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            }
        });
        //反序列化
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        //序列化
        javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


        // 注册JAVA 时间序列化器
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}
