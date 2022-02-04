-- -----------------------------------------------------
-- User market
-- -----------------------------------------------------

CREATE USER 'market'@'localhost'
IDENTIFIED BY 'market';

GRANT ALL ON market.* to 'market'@'localhost';
FLUSH privileges;
