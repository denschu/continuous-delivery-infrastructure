FROM jenkins

USER root

RUN apt-get update  && \
    DEBIAN_FRONTEND=noninteractive         \
    apt-get install -y                     \
        maven rpm            \
        python python-yaml sudo            \
        curl gcc python-pip python-dev  && \
    echo "===> Installing Ansible..."   && \
    pip install ansible                 && \
    echo "===> Removing unused APT resources..."                  && \
    apt-get -f -y --auto-remove remove gcc python-pip python-dev  && \
    apt-get clean                                                 && \
    rm -rf /var/lib/apt/lists/*  /tmp/*

USER jenkins

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/ref/plugins.txt
# TODO Add Job DSL Promotions Plugin

# Jenkins settings
COPY config/config.xml /usr/share/jenkins/ref/config.xml
COPY config/settings.xml /usr/share/maven/conf/settings.xml
COPY config/hudson.tasks.Maven.xml /usr/share/jenkins/ref/hudson.tasks.Maven.xml
COPY config/hudson.tasks.Ansible.xml /usr/share/jenkins/ref/hudson.tasks.Ansible.xml
COPY config/credentials.xml /usr/share/jenkins/ref/credentials.xml

# Adding Job DSL Seed Jobs
# TODO COPY jobs/job-dsl-seed-job.xml /usr/share/jenkins/ref/jobs/job-dsl-seed-job/config.xml

# Temporary: Adding default Jenkins Jobs
COPY jobs/simple-web-rpm.xml /usr/share/jenkins/ref/jobs/simple-web-rpm/config.xml
COPY jobs/simple-wildfly-app.xml /usr/share/jenkins/ref/jobs/simple-wildfly-app/config.xml
COPY jobs/simple-wildfly-app-deploy.xml /usr/share/jenkins/ref/jobs/simple-wildfly-app-deploy/config.xml
