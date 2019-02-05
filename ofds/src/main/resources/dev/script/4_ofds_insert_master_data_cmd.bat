@echo off
powershell -Command "(gc ofds_create_db_and_user_output.sql) -replace 'dbUser', 'mytestuser' | Out-File ofds_create_db_and_user_output.sql"
powershell -Command "(gc ofds_create_db_and_user_output.sql) -replace 'password', 'mytestpassword' | Out-File ofds_create_db_and_user_output.sql"
powershell -Command "(gc ofds_create_db_and_user_output.sql) | Set-Content -Encoding utf8 ofds_create_db_and_user_output.sql"
mysql -hlocalhost -uroot -proot < ofds_create_db_and_user_output.sql