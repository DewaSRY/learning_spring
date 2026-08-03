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

CREATE OR REPLACE VIEW `order_views` AS
SELECT
    o.id as order_id, 
    o.number as order_number,
    o.notes as order_notes,
    o.account_id as account_id,
    o.created_at as order_created_at,
    a.id as account_id,
    a.name as account_name
FROM `order` o
JOIN account a ON o.account_id = a.id;

CREATE OR REPLACE VIEW `order_item_views` AS
SELECT 
    oi.order_id as order_id,
    oi.qty as order_item_qty,
    b.id as beverage_id,
    b.name as beverage_name
FROM order_items oi
JOIN beverage b ON oi.beverage_id = b.id;