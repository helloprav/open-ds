/*--==============================================================================
-- * 
-- * FILE: shop_create_user_and_db.sql   
-- *
-- * MODULE DESCRIPTION:
-- * This script creates a shop@localhost user w/ password shop.
-- * Creates shoppingdb database and gives shop@localhost access to the database.
-- *
-- * (c) COPYRIGHT 2016-2017 NYP Inc.  All Rights Reserved.
-- * NYP Inc. Proprietary.
-- * 
--==============================================================================*/
START TRANSACTION;

-- DROP USER 'schoolusers'@'localhost';
-- DROP USER 'schoolusers'@'%';
-- DROP DATABASE schoolusers;

CREATE DATABASE IF NOT EXISTS ofds
    CHARACTER SET utf8
    COLLATE utf8_general_ci;

CREATE USER 'ofds'@'localhost' IDENTIFIED BY 'ofds';
GRANT ALL PRIVILEGES ON ofds.* TO 'ofds'@'localhost';

CREATE USER 'ofds'@'%' IDENTIFIED BY 'ofds';
GRANT ALL PRIVILEGES ON ofds.* TO 'ofds'@'%';

FLUSH PRIVILEGES;
COMMIT;

