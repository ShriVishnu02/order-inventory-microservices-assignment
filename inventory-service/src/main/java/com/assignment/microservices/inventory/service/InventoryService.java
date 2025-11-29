package com.assignment.microservices.inventory.service;

import com.assignment.microservices.inventory.model.Batch;
import com.assignment.microservices.inventory.model.InventoryUpdateRequest;
import java.util.List;

public interface InventoryService {
    List<Batch> getInventoryBatches(Long productId);
    void updateInventory(InventoryUpdateRequest request);
}
