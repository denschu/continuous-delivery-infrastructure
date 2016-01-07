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

COPY plugins.txt /var/jenkins_home/plugins.txt
RUN /usr/local/bin/plugins.sh /var/jenkins_home/plugins.txt
# Add Job DSL Promotions Plugin

# Jenkins settings
COPY config/config.xml /var/jenkins_home/config.xml
COPY config/settings.xml /usr/share/maven/conf/settings.xml
COPY config/hudson.tasks.Maven.xml /var/jenkins_home/hudson.tasks.Maven.xml
COPY config/hudson.tasks.Ansible.xml /var/jenkins_home//hudson.tasks.Ansible.xml
COPY config/credentials.xml /var/jenkins_home/credentials.xml

# Adding Job DSL Seed Jobs
# COPY jobs/job-dsl-seed-job.xml /var/jenkins_home/jobs/job-dsl-seed-job/config.xml

# Adding default Jenkins Jobs
COPY jobs/simple-web-rpm.xml /var/jenkins_home/jobs/simple-web-rpm/config.xml
COPY jobs/simple-wildfly-app.xml /var/jenkins_home/jobs/simple-wildfly-app.xml
COPY jobs/simple-wildfly-app-deploy.xml /var/jenkins_home/jobs/simple-wildfly-app-deploy.xml
