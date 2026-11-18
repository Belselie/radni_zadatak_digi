CREATE TABLE IF NOT EXISTS user_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    balance_rsd DECIMAL(19,2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transaction_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_account_id BIGINT NOT NULL,
    to_account_id BIGINT NOT NULL,
    amount_rsd DECIMAL(19,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    fail_reason VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_from_account (from_account_id)
);

INSERT IGNORE INTO user_account (id, name, balance_rsd) VALUES (1, 'Alek',1000000.00);
INSERT IGNORE INTO user_account (id, name, balance_rsd) VALUES (2, 'Pera',5000.00);
INSERT IGNORE INTO user_account (id, name, balance_rsd) VALUES (3, 'Zika',500.00);