# Financier

## Prerequisites

- Add environment variables to Gradle template run configuration:
```
POSTGRES_FINANCIER_PASSWORD=###;POSTGRES_FINANCIER_USERNAME=######
```

- Add `financierhost` to `hosts` file

- Run PostgreSQL in Docker on `financierhost` host:
```shell
docker run -d \
  --restart unless-stopped \
  --name postgres_financier \
  -e POSTGRES_USER=### \
  -e POSTGRES_PASSWORD=###### \
  -e POSTGRES_DB=financier \
  -p 54321:5432 \
  postgres:alpine
```
