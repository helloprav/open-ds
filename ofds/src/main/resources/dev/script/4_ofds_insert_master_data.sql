-- -------------------------------------------------------
-- Table `shoppingdb`.`Functions`
-- -------------------------------------------------------
DELETE FROM `shoppingdb`.`employee`;

-- insert superuser
INSERT INTO `shoppingdb`.`employee` (`EmpName`) VALUES ('Praveen Kumar Mishra');

-- insert suppliers
INSERT INTO `shoppingdb`.`supplier` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`) VALUES ('ABC Supplier', '1', now(), 1);
INSERT INTO `shoppingdb`.`supplier` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`) VALUES ('XYZ Supplier', '1', now(), 1);

-- insert product category
INSERT INTO `shoppingdb`.`product_category` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`) VALUES ('Cakes', 1, now(), 1);
INSERT INTO `shoppingdb`.`product_category` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`) VALUES ('Flowers', 1, now(), 1);
INSERT INTO `shoppingdb`.`product_category` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`) VALUES ('Chocolates', 1, now(), 1);

INSERT INTO `shoppingdb`.`product_category` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`, `PARENT_CATEGORY_ID`) VALUES ('Cakes', 1, now(), 1, 1);
INSERT INTO `shoppingdb`.`product_category` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`, `PARENT_CATEGORY_ID`) VALUES ('Eggless Cakes', 1, now(), 1, 1);
INSERT INTO `shoppingdb`.`product_category` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`, `PARENT_CATEGORY_ID`) VALUES ('Suger Free Cakes', 1, now(), 1, 1);
INSERT INTO `shoppingdb`.`product_category` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`, `PARENT_CATEGORY_ID`) VALUES ('Premium Cakes', 1, now(), 1, 1);

INSERT INTO `shoppingdb`.`product_category` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`, `PARENT_CATEGORY_ID`) VALUES ('Flowers', 1, now(), 1, 2);
INSERT INTO `shoppingdb`.`product_category` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`, `PARENT_CATEGORY_ID`) VALUES ('Gift Combos', 1, now(), 1, 2);
INSERT INTO `shoppingdb`.`product_category` (`NAME`, `CREATED_BY`, `CREATED_DATE`, `IS_VALID`, `PARENT_CATEGORY_ID`) VALUES ('Chocolates Bouquets', 1, now(), 1, 2);

-- insert product


commit;

