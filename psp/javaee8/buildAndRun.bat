@echo off
call mvn clean package
call docker build -t org.example/javaee8 .
call docker rm -f javaee8
call docker run -d -p 8080:8080 -p 4848:4848 --name javaee8 org.example/javaee8