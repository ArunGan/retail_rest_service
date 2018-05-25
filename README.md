<b>Technical Assessment - MyRetail RESTful Service</b>
-----------------------------------------
The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API which will aggregate productInfo data from multiple sources and return it as JSON to the caller.

Technologies/Plugin Used
-----------------------------------------
*Java 1.8
*Spring Boot 1.5.8
*Maven
*PMD and Cobertura Plugin
*JUnit 4
*Mockito 1.5.6
*PowerMockito 1.5.6
*MongoDB 3.3
*swagger-codegen-maven-plugin 2.1.5

Use postman or SoapUI

GET request for a valid Product
-----------------------------------------
URL: http://localhost:8080/retail/v1/products/13860428
Select request type: GET
Click button: SEND

GET request for a non existing Product
-----------------------------------------
*URL: http://localhost:8080/retail/v1/products/1386042
*Select request type: GET
*Click button: SEND
*HTTP 404 Not found response returned


POST request
-----------------------------------------
*URL: http://localhost:8080/retail/v1/products/13860428
*Select request type: POST
*Select application type: application/json
*Set raw payload to:
{
        "amount": 52.33,
        "currency_code": "USD"
}

*Click button: SEND
*HTTP 200 Success response returned
*Confirm update by repeating GET request above and checking price

POST request with Bad data
-----------------------------------------
*URL: http://localhost:8080/retail/v1/products/13860428
*Select request type: POST
*Select application type: application/json
*Set raw payload with no amount to:
{

        "currency_code": "USD"
}

*Click button: SEND
*HTTP Status 400 BAD Request response with below payload
{
    "global_response": {
        "explanation_code": "RT300",
        "explanation_message": "Invalid price, should be greated than zero"
    }
}


Production Application
-----------------------------------------
*Add client Authorization(Use HMAC Authorization and TLS Handshake)
*Deploy the swagger documentation in API developer self service portal
*Add a client Identifier/secret key and validate at the API gateway
*Detect spam calls (time between requests, x requests per 1 minute per IP, etc)
*Secure the Mongo database. i.e. Vault
*API versioning : http://localhost:8080/myRetail/v1/products/13860428
*Cache the external API data
*Performance testing with peak load
*Enable functional and regression testing using cucumber and junit framework.