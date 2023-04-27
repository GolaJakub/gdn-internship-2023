# Finance Api

Finance API is a simple runnable project on localhost. It provides 3 endpoints which get data from NBP's api. It also has Swagger UI provided to tests endpoints easier.

## Endpoints

1. http://localhost:8080/average?currencyCode={currencyCode}&date={date}
2. http://localhost:8080/minMaxAverage?currencyCode={currencyCode}&lastQuotations={lastQuotations}
3. http://localhost:8080/majorDifference?currencyCode={currencyCode}&lastQuotations={lastQuotations}

where,
- CurrencyCode is a 3 letters currency code for example "usd", "eur" etc.
- Date is YYYY-MM-DD formatted date from which we want our data
- LastQuotations is amout of last queries (range 1-255)

## Usage

#### To start the server, you should run this command:
```
java -jar gdn-internship-2023-0.0.1-SNAPSHOT.jar
```

#### To test first endpoint you should run this command (it should return 'Average exchange rate: 4.1649'):
```
curl http://localhost:8080/average?currencyCode=usd&date=2023-04-25
```
#### To test second endpoint you should run this command (it should return 'Minimal average exchange rate: 4.5863, and maximum average exchange rate: 4.663'):
```
curl http://localhost:8080/minMaxAverage?currencyCode=eur&lastQuotations=10
```
#### To test third endpoint you should run this command (it should return 'Major difference: 0.1070000000000002'):
```
curl http://localhost:8080/majorDifference?currencyCode=gbp&lastQuotations=25
```
## Swagger UI
#### It also has configured swagger ui available on this link: 
- http://localhost:8080/swagger-ui/index.html#/
