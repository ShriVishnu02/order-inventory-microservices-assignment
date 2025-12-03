package com.assignment.microservices.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch {
    private Long id;
    private Integer quantity;
    private LocalDate expiryDate;
    private String batchNumber;
}
