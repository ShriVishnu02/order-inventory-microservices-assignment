package com.assignment.microservices.inventory.controller;

import com.assignment.microservices.inventory.model.Batch;
import com.assignment.microservices.inventory.model.InventoryUpdateRequest;
import com.assignment.microservices.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/inventory/{productId}")
    public ResponseEntity<List<Batch>> getInventoryByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryBatches(productId));
    }

    @PostMapping("/inventory/update")
    public ResponseEntity<String> updateInventory(@RequestBody InventoryUpdateRequest request) {
        inventoryService.updateInventory(request);
        return ResponseEntity.ok("Inventory updated successfully");
    }
}
