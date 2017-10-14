package com.github.thekingnothing.messageprocessing.message.field;

import com.github.thekingnothing.messageprocessing.message.StringField;

public class Symbol extends StringField {
    public static final int FIELD = 55;
    
    public static Symbol create(String data) {
        return new Symbol(data);
    }
    
    public Symbol(String data) {
        super(FIELD, data);
    }
}
