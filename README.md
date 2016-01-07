# Continuous Delivery Infrastructure

based on:
* Docker
* Jenkins
* Maven
* Ansible

# Prerequisites (MacOSX)

## Install Tools

### Virtualization stuff
```shell
brew cask install virtualbox
brew cask install vagrant
vagrant plugin install vagrant-vbguest
```

### Docker
Install Docker Toolbox: https://www.docker.com/docker-toolbox

### Ansible
```shell
sudo easy_install pip
sudo pip install ansible
```

### Other stuff
```shell
brew install ssh-copy-id
```

## Setup application host based on Vagrant

### Start testserver
```shell
cd testserver
vagrant up
```

### Setup ssh keys
```shell
ssh-keygen # Simply press enter to all questions
ssh-copy-id vagrant@192.168.50.92
or
cat ~/.ssh/id_rsa.pub | ssh vagrant@192.168.50.92 'umask 077; cat >>.ssh/authorized_keys'
ssh vagrant@192.168.50.92
exit
```

## Setup CD infrastructure based on Docker

```shell
docker-compose --x-networking --project-name=cd up
```
or without Docker Compose
```shell
docker network create cd
docker run --rm -it -p 8080:8080 -u root -v /tmp/jenkins:/var/jenkins_home --name cd_jenkins_1 denschu/jenkins
docker run --rm -it -p 8081:8081 --name cd_nexus_1 denschu/nexus
docker run registry:5000/jenkins:latest
```

### Jenkins
```shell
docker build --tag denschu/jenkins .
```

#### Job DSL plugin
TODO Create DSL scripts for example repos (simple-wildfly-app, etc.)

### Nexus
```shell
docker build --tag denschu/nexus .
```

### NGINX
TODO Put HTTP Server in front of Jenkins and Nexus

### Docker Registry

#### Docker Hub
```shell
docker login
docker tag simple-wildfly-app denschu/simple-wildfly-app
docker push denschu/simple-wildfly-app
```

#### Private Registry
```shell
docker run -p 5000:5000 -v /tmp/registry:/var/lib/registry --name registry registry:2
docker tag simple-wildfly-app localhost:5000/simple-wildfly-app
docker push localhost:5000/simple-wildfly-app
```

### Selenium Grid
TODO Setup Selenium Grid with Docker

# Steps

## Build
Execute Build in Jenkins (includes compile, package, unit-tests)
```shell
mvn deploy docker:build
```

## Release
Execute Release in Jenkins (same as build with specific version number)
```shell
mvn build-helper:parse-version versions:set -DnewVersion=1.0.0
mvn deploy docker:build
mvn scm:tag docker:tag
```

## Deploy
```shell
ansible-playbook deployment.yml -i dev/inventory --extra-vars "NAME=simple-wildfly-app VERSION=latest"
```

## Test
TODO Execute Selenium Tests with Jenkins

## Provision
optional step for new hosts!
```shell
ansible-playbook provision.yml -i dev/inventory
```

# Useful commands for testing
Please checkout first the Ansible Example Inventory (https://github.com/denschu/example-ansible-inventory) and switch into the root of this project to be able to execute the following commands.

## Check Ansible setup with test command
```shell
ansible dev -u vagrant -a "/bin/echo hello"
```

## Provision testserver from local shell
```shell
ansible-playbook provision.yml -i dev/inventory
```

## Deploy example application from local shell

### Deploy Docker container
```shell
ansible-playbook deployment.yml -i dev/inventory
```
### Deploy RPM
```shell
ansible-playbook deployment-rpm.yml -i dev/inventory
```
## Go into the container
docker exec -it cd_jenkins_1 /bin/bash
