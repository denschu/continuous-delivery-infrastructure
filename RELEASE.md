# Prepare the CD pipeline for delivery

## Install Docker Registry
ansible-playbook docker-registry.yml --private-key=~/.vagrant.d/insecure_private_key -i ../cdpipeline-ansible-inventory/dev -u vagrant
TODO: Container not running after first try

## Build custom images and push it to the private registry

### Jenkins Master
docker build -t jenkins-master .
docker tag jenkins-master docker.denschu.de/jenkins-master
docker push docker.denschu.de/jenkins-master

### Jenkins Nginx
docker build -t jenkins-nginx .
docker tag jenkins-nginx docker.denschu.de/jenkins-nginx
docker push docker.denschu.de/jenkins-nginx

## Install CD Pipeline (Jenkins, Nexus, Sonar)
ansible-playbook cdpipeline.yml --private-key=~/.vagrant.d/insecure_private_key -i ../cdpipeline-ansible-inventory/dev -u vagrant
