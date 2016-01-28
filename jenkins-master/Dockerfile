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

COPY plugins.txt /plugins.txt
RUN /usr/local/bin/plugins.sh /plugins.txt
# TODO Add Job DSL Promotions Plugin

# Jenkins settings
COPY config/config.xml /usr/share/jenkins/ref/config.xml
COPY config/settings.xml /usr/share/maven/conf/settings.xml
COPY config/hudson.tasks.Maven.xml /usr/share/jenkins/ref/hudson.tasks.Maven.xml
COPY config/org.jenkinsci.plugins.ansible.AnsibleInstallation.xml /usr/share/jenkins/ref/org.jenkinsci.plugins.ansible.AnsibleInstallation.xml
COPY config/credentials.xml /usr/share/jenkins/ref/credentials.xml

# Jenkins settings
COPY plugins/promoted-builds-2.24.2-SNAPSHOT.hpi /usr/share/jenkins/ref/plugins/promoted-builds-2.24.2-SNAPSHOT.hpi

# Adding Job DSL Seed Job
COPY jobs/job-dsl-seed-job.xml /usr/share/jenkins/ref/jobs/job-dsl-seed-job/config.xml
