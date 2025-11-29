INSERT INTO products (id, name) VALUES (1, 'A');
INSERT INTO products (id, name) VALUES (2, 'B');
INSERT INTO products (id, name) VALUES (3, 'C');

INSERT INTO batches (id, product_id, quantity, expiry_date, batch_number) VALUES (1, 1, 100, '2025-12-31', 'BATCH-001');
INSERT INTO batches (id, product_id, quantity, expiry_date, batch_number) VALUES (2, 1, 150, '2026-06-30', 'BATCH-002');