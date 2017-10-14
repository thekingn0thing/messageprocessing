package com.github.thekingnothing.messageprocessing.message.dictionary;

import com.github.thekingnothing.messageprocessing.message.Field;
import com.github.thekingnothing.messageprocessing.message.field.AdjustmentOperationType;
import com.github.thekingnothing.messageprocessing.message.field.MessageTypeField;
import com.github.thekingnothing.messageprocessing.message.field.Price;
import com.github.thekingnothing.messageprocessing.message.field.Quantity;
import com.github.thekingnothing.messageprocessing.message.field.Symbol;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MessageDictionary {
    
    public static MessageDictionary create() {
        final MessageDictionary messageDictionary = new MessageDictionary();
        
        messageDictionary.addTag(AdjustmentOperationType.FIELD, AdjustmentOperationType.class);
        messageDictionary.addTag(Price.FIELD, Price.class);
        messageDictionary.addTag(Quantity.FIELD, Quantity.class);
        messageDictionary.addTag(Symbol.FIELD, Symbol.class);
        messageDictionary.addTag(MessageTypeField.FIELD, MessageTypeField.class);
        
        messageDictionary.addConverter(BigDecimal.class, new BigDecimalConverter());
        messageDictionary.addConverter(Integer.class, new IntegerConverter());
        
        return messageDictionary;
    }
    
    private final Map<Integer, Class<? extends Field>> dictionary;
    
    private final Map<Class, Converter> converters;
    
    private MessageDictionary() {
        dictionary = new HashMap<>();
        converters = new HashMap<>();
    }
    
    private <T> void addConverter(final Class<T> klass, final Converter<T> converter) {
        converters.put(klass, converter);
    }
    
    public Field parseField(final int tag, final String value) throws Exception {
        final Class<? extends Field> fieldClass = dictionary.get(tag);
        
        ParameterizedType type = (ParameterizedType) fieldClass.getSuperclass().getGenericSuperclass();
        final Class<?> data = (Class<?>) type.getActualTypeArguments()[0];
        
        final Object val;
        if (data.isAssignableFrom(String.class)) {
            val = value;
        } else {
            final Converter converter = converters.get(data);
            val = converter.convert(value);
        }
        
        final Constructor<? extends Field> constructor = fieldClass.getConstructor(data);
        
        return constructor.newInstance(val);
    }
    
    private void addTag(final int field, final Class<? extends Field> fieldClass) {
        dictionary.put(field, fieldClass);
    }
    
    private interface Converter<T> {
        T convert(String source);
    }
    
    
    private static class BigDecimalConverter implements Converter<BigDecimal> {
        @Override
        public BigDecimal convert(final String source) {
            return new BigDecimal(source);
        }
    }
    
    private static class IntegerConverter implements Converter<Integer> {
        @Override
        public Integer convert(final String source) {
            return Integer.parseInt(source);
        }
    }
}
