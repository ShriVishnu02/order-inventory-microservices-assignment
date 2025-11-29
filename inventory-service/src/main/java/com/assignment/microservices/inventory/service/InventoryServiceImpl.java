package com.assignment.microservices.inventory.service;

import com.assignment.microservices.inventory.model.Batch;
import com.assignment.microservices.inventory.model.InventoryUpdateRequest;
import com.assignment.microservices.inventory.service.factory.InventoryHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryHandlerFactory handlerFactory;

    @Transactional
    @Override
    public List<Batch> getInventoryBatches(Long productId) {
        return handlerFactory.getHandler("expiryBased").getBatches(productId);
    }

    @Transactional
    @Override
    public void updateInventory(InventoryUpdateRequest request) {
        handlerFactory.getHandler("expiryBased").updateInventory(
                request.getProductId(),
                request.getQuantity()
        );
    }
}
