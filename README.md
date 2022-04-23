# Financier

## Scripts

```
git config --global alias.lgg "log --graph --abbrev-commit --decorate --all     --format=format:'%C(bold blue)%h%C(reset) - %an%C(reset) %C(bold green)(%ar)%C(reset)%C(bold yellow)%d%C(reset) %C(white)%s%C(reset)'" && \
git config --global alias.lg  "log --graph --abbrev-commit --decorate --all -15 --format=format:'%C(bold blue)%h%C(reset) - %an%C(reset) %C(bold green)(%ar)%C(reset)%C(bold yellow)%d%C(reset) %C(white)%s%C(reset)'"

docker run -d --name postgres-test \
    --restart unless-stopped \
    -p 5432:5432 \
    -e POSTGRES_PASSWORD=postgres \
    -e POSTGRES_DB=financier_dev \
    -v postgresql_data:/var/lib/postgresql/data \
    postgres:alpine
```
