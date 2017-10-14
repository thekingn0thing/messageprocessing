package com.github.thekingnothing.messageprocessing.utils;

public class StringUtils {
    
    public static String central(String str, int size) {
        if (str == null || size <= 0) {
            return str;
        }
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) {
            return str;
        }
        str = leftPad(str, strLen + pads / 2);
        str = rightPad(str, size);
        return str;
    }
    
    private static String rightPad(final String str, final int size) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
    
        StringBuilder pad = new StringBuilder();
        for (int i = 0; i < pads; i++) {
            pad.append(" ");
        }
    
        return str.concat(pad.toString());
    }
    
    private static String leftPad(final String str, final int size) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        
        StringBuilder pad = new StringBuilder();
        for (int i = 0; i < pads; i++) {
            pad.append(" ");
        }
        
        return pad.toString().concat(str);
    }
}
