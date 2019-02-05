-- insert admin user
INSERT INTO `user` (`USERNAME`, `PASSWORD`, `FIRST_NAME`, `CREATED_DATE`, `IS_VALID`, `EMAIL`, `GENDER`, `STATUS`, `CREATED_BY`) VALUES ('admin', 'uSJ90JFrx+gS7hePWC6R4w==', 'Admin', now(), 1, 'admin@myaapp.com', 'male', 'active', '1');

commit;

