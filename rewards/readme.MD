### How to start the application?

For starting the application please import the application in any of the preferred IDE (Intellij, Eclipse, STS, etc.)

Application can be started by executing the main method present in class RewardsApplication.


### API Specification

And you can access swagger using the url : `http://localhost:8080/swagger-ui.html`

GET API for fetching customer specific rewards:
`http://localhost:8080/api/rewards?customerId=1`

GET API for fetching all customer specific rewards:
`http://localhost:8080/api/rewards/all`

POST API for adding a new purchase:
`http://localhost:8080/api/purchase`

Request Data:

```
{
    "customerId":1,
    "productName":"iPhone 14",
    "amount":1000.0
}
```