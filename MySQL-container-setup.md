MySQL DB - Docker Configuration:

Pull the docker image : docker pull mysql:8.0
Run MySQL container:  docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=your_password -p 3306:3306 -v mysql-data:/var/lib/mysql -d mysql:8.0 (Replace your_password with a strong password.mysql-data is the name of the Docker volume used for persistence.)
Verify container is running or not: docker ps
Connecting to running container: docker exec -it mysql-container bash
Connecting to MySQL:      mysql -u root -p
Creating DB and a User:
     CREATE DATABASE my_database;
     CREATE USER 'my_user'@'%' IDENTIFIED BY 'my_password';
     GRANT ALL PRIVILEGES ON my_database.* TO 'my_user'@'%';
     FLUSH PRIVILEGES;
