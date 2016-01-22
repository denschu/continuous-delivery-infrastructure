# Prepare the CD pipeline for delivery

## Build images and push it to the private registry

docker build -t jenkins-master .
docker tag jenkins-master denschu.de/jenkins-master
docker push denschu.de/jenkins-master
