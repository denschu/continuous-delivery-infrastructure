FROM sonatype/nexus:oss

USER root

RUN yum install -y unzip \
 yum clean all

# install nexus-p2-repository-plugin
RUN curl --fail --silent --location --retry 3 \
    -o /tmp/rundeck-nexus-plugin-2.11.1-01-bundle.zip \
    https://github.com/moifort/nexus-rundeck-plugin/releases/download/2.11.1-01/rundeck-nexus-plugin-2.11.1-01-bundle.zip \
  && unzip -d /opt/sonatype/nexus/nexus/WEB-INF/plugin-repository \
    /tmp/rundeck-nexus-plugin-2.11.1-01-bundle.zip \
  && find /opt/sonatype/nexus/nexus/WEB-INF/plugin-repository/rundeck-nexus-plugin-2.11.1-01 \
    -type d -exec chmod 755 {} \; \
  && find /opt/sonatype/nexus/nexus/WEB-INF/plugin-repository/rundeck-nexus-plugin-2.11.1-01 \
    -type f -exec chmod 644 {} \; \
  && rm /tmp/rundeck-nexus-plugin-2.11.1-01-bundle.zip

USER nexus
