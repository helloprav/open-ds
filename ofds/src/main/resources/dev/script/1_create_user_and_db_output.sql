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

CREATE DATABASE IF NOT EXISTS ofdsdb CHARACTER SET utf8 COLLATE utf8_general_ci;

/*
 * why drop user and flush priviledge required before create user
 * https://stackoverflow.com/questions/5555328/error-1396-hy000-operation-create-user-failed-for-jacklocalhost
 */
drop user IF EXISTS 'ofdsuser'@'localhost';
flush privileges;
CREATE USER 'ofdsuser'@'localhost' IDENTIFIED BY 'ofdspassword';
GRANT ALL PRIVILEGES ON ofdsdb.* TO 'ofdsuser'@'localhost';

drop user IF EXISTS 'ofdsuser'@'%';
flush privileges;
CREATE USER 'ofdsuser'@'%' IDENTIFIED BY 'ofdspassword';
GRANT ALL PRIVILEGES ON ofdsdb.* TO 'ofdsuser'@'%';

FLUSH PRIVILEGES;
COMMIT;
