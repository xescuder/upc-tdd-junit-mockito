


```shell
mvn clean install -DskipTests
docker build .
docker-compose down -v
docker-compose up -d
```


Open Postman and create a new request: `localhost:8090/api/users`

You should get:

```json
[
    {
        "id": 1,
        "firstName": "Xavier",
        "lastName": "Escudero Sabadell",
        "email": "xescuder@gmail.com",
        "password": null,
        "registrationDate": null
    }
]
```