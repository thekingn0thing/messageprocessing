package com.github.thekingnothing.messageprocessing.message;

public enum  MessageType {
    SINGLE_ORDER(1),
    QUANTITY_ORDER(2),
    ADJUSTMENT(3);
    
    private final int type;
    
    MessageType(final int type) {
        this.type = type;
    }
    
    public int getType() {
        return type;
    }
    
    public static MessageType find(final int type) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.type == type){
                return messageType;
            }
        }
        throw new IllegalArgumentException("Unknown type " + type);
    }
}
