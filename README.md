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

### Vagrant

See [Vagrant Installation Docs](https://docs.vagrantup.com/v2/installation/) for other operating systems.

### Docker
See [Docker](https://docs.docker.com/linux/) for installation instructions. If you are on a Mac or a Windows system please use [Docker Toolbox](https://www.docker.com/docker-toolbox).

### Ansible
See [Ansible Docs](http://docs.ansible.com/ansible/intro_installation.html)

## Setup

There are two ways to start and play around with the CD infrastructure. The recommended and most complete way is to use [Vagrant](https://www.vagrantup.com/) which starts up all containers that are needed to build, release, test, deploy and run the example application. With Docker Compose you can just run Jenkins, Nexus and Sonar at the moment.

### Vagrant

```shell
cd testserver
vagrant plugin install vagrant-hostmanager
vagrant up
```

### Docker Compose

Create default host for CD infrastructure:

```shell
docker-machine create --driver virtualbox --virtualbox-memory "4096" --virtualbox-disk-size "40000" --engine-insecure-registry docker.denschu.de default
```

Start the infrastructure:

```shell
docker-compose up
```

## Access Tools

### Vagrant hosts

| *Tool* | *Link* | *Credentials* |
| ------------- | ------------- | ------------- |
| Jenkins | https://jenkins.denschu.de | no login required |
| Nexus | https://nexus.denschu.de | admin/password |
| SonarQube | http://sonar.denschu.de:9000/ | admin/admin |
| Bitbucket | https://git.denschu.de | no login required |
| Docker Registry | https://docker.denschu.de/v2/_catalog | no login required |

### Docker machine hosts (on Mac OS X/Windows)

| *Tool* | *Link* | *Credentials* |
| ------------- | ------------- | ------------- |
| Jenkins | http://${docker-machine ip default}:8080/jenkins/ | no login required |
| SonarQube | http://${docker-machine ip default}:9000/ | admin/admin |
| Nexus | http://${docker-machine ip default}:8081/nexus | admin/password |

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
