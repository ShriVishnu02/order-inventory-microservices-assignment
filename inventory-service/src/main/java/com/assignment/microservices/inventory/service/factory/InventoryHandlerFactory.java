package com.assignment.microservices.inventory.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class InventoryHandlerFactory {
    private final Map<String, InventoryHandler> handlers;

    public InventoryHandlerFactory(Map<String, InventoryHandler> handlers) {
        this.handlers = handlers;
    }

    public InventoryHandler getHandler(String type) {
        return handlers.getOrDefault(type, handlers.get("expiryBased"));
    }
}
