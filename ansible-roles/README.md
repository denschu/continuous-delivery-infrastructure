# Ansible Playbooks

### Provision

```shell
ansible-playbook docker-registry.yml --private-key=~/.vagrant.d/insecure_private_key -i ../cdpipeline-ansible-inventory/dev -u vagrant
ansible-playbook cdpipeline.yml --private-key=~/.vagrant.d/insecure_private_key -i ../cdpipeline-ansible-inventory/dev -u vagrant
ansible-playbook jboss.yml --private-key=~/.vagrant.d/insecure_private_key -i ../example-ansible-inventory/dev/inventory -u vagrant
```

### Deploy JBoss application

```shell
ansible-playbook deploy-jboss-application.yml -i ../example-ansible-inventory/dev/inventory --extra-vars "image_name=jboss/wildfly container_name=wildfly version=latest"
```
ansible-playbook deploy-jboss-application.yml --private-key=~/.vagrant.d/insecure_private_key -i ../example-ansible-inventory/dev/inventory

### Undeploy JBoss application

```shell
ansible-playbook undeploy-jboss-application.yml -i ../example-ansible-inventory/dev/inventory --extra-vars "image_name=jboss/wildfly container_name=wildfly"
```
