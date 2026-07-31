CREATE TABLE IF NOT EXISTS `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(255) UNIQUE,
    notes TEXT,
    account_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (beverage_id) REFERENCES beverage(id)
);

CREATE TABLE IF NOT EXISTS order_items (
    order_id BIGINT NOT NULL,
    beverage_id BIGINT NOT NULL,
    qty INT NOT NULL,
    PRIMARY KEY (order_id, beverage_id),
    FOREIGN KEY (order_id) REFERENCES `order`(id),
    FOREIGN KEY (beverage_id) REFERENCES beverage(id)
);




