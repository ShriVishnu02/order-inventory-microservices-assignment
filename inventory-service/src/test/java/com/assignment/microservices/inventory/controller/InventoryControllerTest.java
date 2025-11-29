package com.assignment.microservices.inventory.controller;

import com.assignment.microservices.inventory.model.Batch;
import com.assignment.microservices.inventory.model.InventoryUpdateRequest;
import com.assignment.microservices.inventory.model.Product;
import com.assignment.microservices.inventory.repository.BatchRepository;
import com.assignment.microservices.inventory.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Product testProduct;
    private Batch testBatch;

    @BeforeEach
    void setUp() {
        batchRepository.deleteAll();
        productRepository.deleteAll();

        testProduct = new Product();
        testProduct.setName("Product A");
        testProduct = productRepository.save(testProduct);

        testBatch = new Batch();
        testBatch.setProduct(testProduct);
        testBatch.setQuantity(100);
        testBatch.setExpiryDate(LocalDate.of(2025, 12, 31));
        testBatch.setBatchNumber("BATCH-001");
        testBatch = batchRepository.save(testBatch);
    }

    @Test
    void testGetInventoryByProductEndpoint() throws Exception {
        mockMvc.perform(get("/inventory/" + testProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(100))
                .andExpect(jsonPath("$[0].batchNumber").value("BATCH-001"));
    }

    @Test
    void testUpdateInventoryEndpoint() throws Exception {
        InventoryUpdateRequest request = new InventoryUpdateRequest(testProduct.getId(), 50);

        mockMvc.perform(post("/inventory/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetInventoryByProductNotFound() throws Exception {
        mockMvc.perform(get("/inventory/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
