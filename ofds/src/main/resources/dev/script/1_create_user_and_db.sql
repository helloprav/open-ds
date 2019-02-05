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

CREATE DATABASE IF NOT EXISTS dbName CHARACTER SET utf8 COLLATE utf8_general_ci;

/*
 * why drop user and flush priviledge required before create user
 * https://stackoverflow.com/questions/5555328/error-1396-hy000-operation-create-user-failed-for-jacklocalhost
 */
drop user IF EXISTS 'dbUser'@'localhost';
flush privileges;
CREATE USER 'dbUser'@'localhost' IDENTIFIED BY 'dbPassword';
GRANT ALL PRIVILEGES ON dbName.* TO 'dbUser'@'localhost';

drop user IF EXISTS 'dbUser'@'%';
flush privileges;
CREATE USER 'dbUser'@'%' IDENTIFIED BY 'dbPassword';
GRANT ALL PRIVILEGES ON dbName.* TO 'dbUser'@'%';

FLUSH PRIVILEGES;
COMMIT;
