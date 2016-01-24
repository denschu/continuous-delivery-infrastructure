# Prepare the CD pipeline for delivery

## Build images and push it to the private registry

docker build -t jenkins-master .
docker tag jenkins-master denschu.de/jenkins-master
docker push denschu.de/jenkins-master

docker run -p 8080:8080 -p 50000:50000 -v /data/jenkins:/var/jenkins_home denschu.de/jenkins-master
