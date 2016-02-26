FROM haproxy:1.6

ENV HAPROXY_USER haproxy

RUN groupadd --system ${HAPROXY_USER} && \
  useradd --system --gid ${HAPROXY_USER} -u 1000 ${HAPROXY_USER} && \
  mkdir --parents /var/lib/${HAPROXY_USER} && \
  chown -R ${HAPROXY_USER}:${HAPROXY_USER} /var/lib/${HAPROXY_USER}

COPY haproxy.cfg /etc/haproxy/haproxy.cfg

# Define mountable directories.
VOLUME ["/etc/haproxy"]

EXPOSE 80 443 1936

CMD ["haproxy", "-db", "-f", "/etc/haproxy/haproxy.cfg"]
