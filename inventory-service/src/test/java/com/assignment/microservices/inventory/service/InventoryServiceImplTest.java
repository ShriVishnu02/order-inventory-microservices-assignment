package com.assignment.microservices.inventory.service;

import com.assignment.microservices.inventory.model.InventoryUpdateRequest;
import com.assignment.microservices.inventory.service.factory.InventoryHandler;
import com.assignment.microservices.inventory.service.factory.InventoryHandlerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryHandlerFactory handlerFactory;

    @Mock
    private InventoryHandler inventoryHandler;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @BeforeEach
    void setUp() {
        when(handlerFactory.getHandler(anyString())).thenReturn(inventoryHandler);
    }

    @Test
    void testGetInventoryBatches() {
        inventoryService.getInventoryBatches(1L);

        verify(inventoryHandler, times(1)).getBatches(1L);
    }

    @Test
    void testUpdateInventory() {
        InventoryUpdateRequest request = new InventoryUpdateRequest(1L, 5);

        inventoryService.updateInventory(request);

        verify(inventoryHandler, times(1)).updateInventory(1L, 5);
    }
}
