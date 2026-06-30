CREATE TABLE orders (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id     VARCHAR(255) NOT NULL,
    cart_id         UUID NOT NULL,
    purchase_total  NUMERIC(10,2) NOT NULL,
    latest_status   VARCHAR(50) NOT NULL DEFAULT 'PLACED',
    created_at      TIMESTAMP NOT NULL,
    deleted_at      TIMESTAMP
);
