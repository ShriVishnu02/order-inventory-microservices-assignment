package com.assignment.microservices.inventory.service.factory;

import com.assignment.microservices.inventory.model.Batch;
import java.util.List;

public interface InventoryHandler {
    List<Batch> getBatches(Long productId);
    void updateInventory(Long productId, Integer quantity);
}

