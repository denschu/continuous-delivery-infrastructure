# Continuous Delivery Infrastructure

based on:
* Docker
* Jenkins
* Maven
* Ansible

related Git Repositories:
* Example Ansible Inventory: https://github.com/denschu/example-ansible-inventory
* CD Pipeline Ansible Inventory: https://github.com/denschu/cdpipeline-ansible-inventory
* Ansible Playbooks: https://github.com/denschu/ansible-playbooks
* Example Application: https://bitbucket.org/denschu/example-eap-application
* Job DSLs: https://github.com/denschu/job-dsl-repository

![Overview](cd-infrastructure.png)

## Install Tools

### Virtualization stuff

See [Vagrant Installation Docs](https://docs.vagrantup.com/v2/installation/) for other operating systems.

### Docker
See [Docker Toolbox](https://www.docker.com/docker-toolbox)

### Ansible
See [Ansible Docs](http://docs.ansible.com/ansible/intro_installation.html)

## Setup

### Mac OS X

Create default host for CD infrastructure
```shell
docker-machine create --driver virtualbox --virtualbox-memory "4096" --virtualbox-disk-size "40000" --engine-insecure-registry docker.denschu.de default
```

## Start the infrastructure locally

```shell
eval $(docker-machine env default)
docker-compose up
docker-compose -f docker-compose-registry.yml up -d
```

## Access Tools

| *Tool* | *Link* | *Credentials* |
| ------------- | ------------- | ------------- |
| Jenkins | http://${docker-machine ip default}:8080/jenkins/ | no login required |
| SonarQube | http://${docker-machine ip default}:9000/ | admin/admin |
| Nexus | http://${docker-machine ip default}:8081/nexus | admin/password |
| Bitbucket | http://${docker-machine ip default}:7990 | no login required |

## Setup example application host based on Vagrant

### Start testserver
```shell
cd testserver
vagrant plugin install vagrant-hostmanager
vagrant up
```

## Steps

The following steps are part of the generated Jenkins jobs.

### Build
Execute Build in Jenkins (includes compile, package, unit-tests)
```shell
mvn deploy docker:build
```

### Release
Execute Release in Jenkins (same as build with specific version number)
```shell
mvn build-helper:parse-version versions:set -DnewVersion=1.0.0
mvn deploy docker:build
mvn scm:tag docker:tag
```
### Deploy
```shell
ansible-playbook deployment.yml -i ../example-ansible-inventory/dev/inventory --extra-vars "NAME=simple-wildfly-app VERSION=latest"
```
### Test
Execute Selenium Tests with Jenkins

### Provision
optional step for new application hosts!
```shell
ansible-playbook jboss.yml -i dev/inventory
```
