package com.github.thekingnothing.messageprocessing.processing;


import com.github.thekingnothing.messageprocessing.storage.Storage;

public interface PostProcessor {
    void afterMessage(Storage storage);
}
