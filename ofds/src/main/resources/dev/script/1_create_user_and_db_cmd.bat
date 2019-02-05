powershell -Command "(gc 1_create_user_and_db.sql) -replace 'dbName', 'ofdsdb' | Out-File 1_create_user_and_db_output.sql"
powershell -Command "(gc 1_create_user_and_db_output.sql) -replace 'dbUser', 'ofdsuser' | Out-File 1_create_user_and_db_output.sql"
powershell -Command "(gc 1_create_user_and_db_output.sql) -replace 'dbPassword', 'ofdspassword' | Out-File 1_create_user_and_db_output.sql"
powershell -Command "(gc 1_create_user_and_db_output.sql) | Set-Content -Encoding utf8 1_create_user_and_db_output.sql"
mysql -hlocalhost -uroot -proot < 1_create_user_and_db_output.sql