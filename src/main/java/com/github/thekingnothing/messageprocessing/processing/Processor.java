package com.github.thekingnothing.messageprocessing.processing;

import com.github.thekingnothing.messageprocessing.message.Message;

public interface Processor {
    void process(Message message);
}
