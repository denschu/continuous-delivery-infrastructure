sudo openssl req -newkey rsa:4096 -nodes -sha256 -keyout certs/domain.key -x509 -days 365 -out certs/domain.crt
openssl x509 -in certs/domain.crt -text

domain.crt/key: *.denschu.de
docker.crt/key: docker.denschu.de
