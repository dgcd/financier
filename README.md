[![pipeline](https://github.com/dgcd/financier/actions/workflows/pipeline.yml/badge.svg)](https://github.com/dgcd/financier/actions/workflows/pipeline.yml)

# Financier

Simple personal finance manager and playground for new features. Clean architecture and Java modularity implementation.

## Features

* Spring Boot 3
* Clean architecture
* Java modularity
* SpringDoc OpenAPI UI
* Apache POI OOXML
* Front: Vue.js 2.7

## Uses

* JDK 21
* PostgreSQL 16
* Node.js 20

## Prerequisites

* Add environment variables to Gradle run configuration template in IDE:

```
POSTGRES_FINANCIER_USERNAME=##;POSTGRES_FINANCIER_PASSWORD=####;POSTGRES_FINANCIER_PORT=######;EXCHANGE_API_KEY=##
```

* Add `financierhost` to `hosts` file

* Run PostgreSQL in Docker/Podman on `financierhost` host:

```shell
docker run -d \
  --restart unless-stopped \
  --name postgres_financier \
  -e POSTGRES_USER=## \
  -e POSTGRES_PASSWORD=#### \
  -e POSTGRES_DB=financier \
  -p ######:5432 \
  postgres:alpine
```

* Additionally create databases: financier_dev, financier_test

* Go to `front/` subfolder and run `npm install` and `npm run build`
