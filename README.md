# Financier

## Скрипты установки

```
sudo apt update && sudo apt upgrade -y && sudo apt autoremove -y && sudo snap refresh

ssh-keygen -t rsa -N '' -f ~/.ssh/id_rsa

sudo apt install -y curl vim git

git config --global alias.lgg "log --graph --abbrev-commit --decorate --all     --format=format:'%C(bold blue)%h%C(reset) - %an%C(reset) %C(bold green)(%ar)%C(reset)%C(bold yellow)%d%C(reset) %C(white)%s%C(reset)'" && \
git config --global alias.lg  "log --graph --abbrev-commit --decorate --all -15 --format=format:'%C(bold blue)%h%C(reset) - %an%C(reset) %C(bold green)(%ar)%C(reset)%C(bold yellow)%d%C(reset) %C(white)%s%C(reset)'" && \
git config --global user.name "######" && \
git config --global user.email "#######"

sudo snap install postman dbeaver-ce && \
sudo snap install code intellij-idea-community --classic

curl -fsSL https://deb.nodesource.com/setup_current.x | sudo -E bash - && \
sudo apt update && sudo apt install -y nodejs

sudo apt install -y apt-transport-https ca-certificates gnupg-agent software-properties-common && \
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - && \
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" && \
sudo apt update && sudo apt install -y docker-ce docker-ce-cli containerd.io docker-compose && \
sudo usermod -aG docker $USER


npm install -g @vue/cli @vue/cli-service-global

# install VS Code Extensions: Vetur, EditorConfig for VS Code


docker run -d --name postgres \
    --restart unless-stopped \
    -p 5432:5432 \
    -e POSTGRES_PASSWORD=postgres \
    -e POSTGRES_DB=financier \
    -v postgresql_data:/var/lib/postgresql/data \
    postgres:alpine

```
