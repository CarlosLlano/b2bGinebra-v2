# DESPLIEGUE CON DOCKER #

***Comandos***
```bash
docker-compose up -d
```

*Acceso en Linux y Windows*: http://localhost:8080/b2bGinebra/inicio.xhtml

*Acceso en versiones Windows diferentes de Windows 10 pro y enterprise*: http://192.168.99.100:8080/b2bGinebra/inicio.xhtml



***Problemas***

ERROR: throwable while attempting to get a new connection null 

![error](https://user-images.githubusercontent.com/17281733/34600144-0266a9f8-f1c4-11e7-846d-5b74ccd37ea0.jpeg)

SOLUCION: ejecutar servicios uno por uno
```bash
docker-compose up -d postgresdb
docker-compose up -d wildfly
```
