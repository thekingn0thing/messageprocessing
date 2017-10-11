package com.github.thekingnothing.messageprocessing.message;

public class StringField extends Field<String> {
    public static StringField NULL = new StringField(-1, "");
    
    public StringField(final int tag, final String data) {
        super(tag, data);
    }
}
