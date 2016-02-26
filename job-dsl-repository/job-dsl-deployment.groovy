String baseUrl = "https://api.bitbucket.org"
String version = "2.0"
String organization = "denschu"
def repositoriesUrl = new URL([baseUrl, version, "repositories", organization].join("/"))
def repositoriesJson = new groovy.json.JsonSlurper().parse(repositoriesUrl.newReader())

repositoriesJson.values.each {
  def username = it.owner.username
  def projectName = it.name
  def language = it.language
  def pom = "https://api.bitbucket.org/1.0/repositories/${username}/${projectName}/raw/master/pom.xml";
  if(exists(pom)){

    freeStyleJob("${projectName}-deployment") {
      multiscm {
          git {
              remote {
                  url("https://github.com/denschu/ansible-playbooks")
              }
              branch('master')
              relativeTargetDir('ansible-playbooks')
          }
          git {
              remote {
                  url("https://github.com/denschu/example-ansible-inventory")
              }
              branch('master')
              relativeTargetDir('example-ansible-inventory')
          }
      }
      steps {
          ansiblePlaybook('ansible-playbooks/deploy-jboss-application.yml') {
              ansibleName('ansible')
              credentialsId('9d6921d1-5c90-4038-bb43-4866613353de')
              inventoryPath('example-ansible-inventory/dev')
              sudo(true)
          }
      }
    }

  }
}

def exists(String url) {
    HttpURLConnection huc =  (HttpURLConnection)new URL(url).openConnection();
    huc.setRequestMethod ("HEAD");
    huc.connect();
  	if(huc.getResponseCode() == 200){
  		return true;
    }
  	return false;
}
