package com.github.thekingnothing.messageprocessing.report;

import com.github.thekingnothing.messageprocessing.storage.Storage;

public interface ReportFactory {
    Report createReport(Storage storage);
}
