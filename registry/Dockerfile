FROM registry:2

MAINTAINER Dennis Schulte <dennis.schulte@codecentric.de>

ADD config.yml /etc/docker-registry/

VOLUME /docker-registry-storage

ENV DOCKER_REGISTRY_CONFIG /etc/docker-registry/config.yml
ENV SETTINGS_FLAVOR file

ENV GUNICORN_WORKERS 8
ENV GUNICORN_GRACEFUL_TIMEOUT 3600
ENV GUNICORN_SILENT_TIMEOUT 3600

CMD ["docker-registry"]
