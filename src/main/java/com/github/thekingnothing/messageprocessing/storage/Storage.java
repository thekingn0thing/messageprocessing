package com.github.thekingnothing.messageprocessing.storage;

import com.github.thekingnothing.messageprocessing.model.Adjustment;
import com.github.thekingnothing.messageprocessing.model.Order;
import com.github.thekingnothing.messageprocessing.model.SymbolAdjustment;
import com.github.thekingnothing.messageprocessing.model.Total;

import java.util.List;

public interface Storage {
    void storeOrder(final Order order);
    
    void storeAdjustment(Adjustment adjustment);
    
    List<Order> allOrders();
    
    List<SymbolAdjustment> allAdjustment();
    
    void storeTotal(String symbol, Total total);
    
    Total getTotal(String symbol);
    
    List<Order> ordersForSymbol(String symbol);
    
    void truncateOrders(String symbol);
}
