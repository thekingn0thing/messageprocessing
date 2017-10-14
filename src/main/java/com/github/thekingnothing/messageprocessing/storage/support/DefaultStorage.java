package com.github.thekingnothing.messageprocessing.storage.support;


import com.github.thekingnothing.messageprocessing.model.Adjustment;
import com.github.thekingnothing.messageprocessing.model.Order;
import com.github.thekingnothing.messageprocessing.model.SymbolAdjustment;
import com.github.thekingnothing.messageprocessing.model.Total;
import com.github.thekingnothing.messageprocessing.storage.Storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultStorage implements Storage {
    
    private final Map<String, List<Order>> orders;
    private final Map<String, List<Adjustment>> adjustments;
    private final Map<String, Total> totals;
    
    public DefaultStorage() {
        orders = new HashMap<>();
        adjustments = new HashMap<>();
        totals = new HashMap<>();
    }
    
    @Override
    public void storeOrder(final Order order) {
        getOrderLog(order.getSymbol()).add(order);
    }
    
    @Override
    public void storeAdjustment(final Adjustment adjustment) {
        final List<Adjustment> adjustmentLog = getAdjustmentLog(adjustment.getSymbol());
        adjustmentLog.add(adjustment);
    }
    
    @Override
    public List<Order> allOrders() {
        return orders.values().stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<SymbolAdjustment> allAdjustment() {
        return adjustments.entrySet().stream()
                          .map(e -> new SymbolAdjustment(e.getKey(), e.getValue()))
                          .collect(Collectors.toList());
    }
    
    @Override
    public void storeTotal(final String symbol, final Total total) {
        totals.put(symbol, total);
    }
    
    @Override
    public Total getTotal(final String symbol) {
        return totals.getOrDefault(symbol, new Total());
    }
    
    @Override
    public List<Order> ordersForSymbol(final String symbol) {
        return new ArrayList<>(getOrderLog(symbol));
    }
    
    @Override
    public void truncateOrders(final String symbol) {
        getOrderLog(symbol).clear();
    }
    
    @Override
    public Map<String, Total> allTotals() {
        return new HashMap<>(totals);
    }
    
    @Override
    public boolean hasOrdersForSymbol(final String symbol) {
        return orders.containsKey(symbol);
    }
    
    private List<Adjustment> getAdjustmentLog(final String symbol) {
        return adjustments.computeIfAbsent(symbol, s -> new ArrayList<>());
    }
    
    private List<Order> getOrderLog(final String symbol) {
        return orders.computeIfAbsent(symbol, s -> new ArrayList<>());
    }
}
