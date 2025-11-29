package com.assignment.microservices.inventory.service.factory;

import com.assignment.microservices.inventory.model.Batch;
import com.assignment.microservices.inventory.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("expiryBased")
public class ExpiryBasedInventoryHandler implements InventoryHandler {
    @Autowired
    private BatchRepository batchRepository;

    @Override
    public List<Batch> getBatches(Long productId) {
        return batchRepository.findByProductIdOrderByExpiryDateAsc(productId);
    }

    @Override
    public void updateInventory(Long productId, Integer quantity) {
        List<Batch> batches = getBatches(productId);
        int remaining = quantity;
        for (Batch batch : batches) {
            if (remaining <= 0) break;
            int available = batch.getQuantity();
            if (available >= remaining) {
                batch.setQuantity(available - remaining);
                remaining = 0;
            } else {
                batch.setQuantity(0);
                remaining -= available;
            }
            batchRepository.save(batch);
        }
    }
}
