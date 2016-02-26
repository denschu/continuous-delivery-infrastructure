String baseUrl = "https://api.bitbucket.org"
String version = "2.0"
String organization = "denschu"
def repositoriesUrl = new URL([baseUrl, version, "repositories", organization].join("/"))
def repositoriesJson = new groovy.json.JsonSlurper().parse(repositoriesUrl.newReader())

repositoriesJson.values.each {
  def username = it.owner.username
  def projectName = it.name
  def language = it.language
  println "Repository: ${username}/${projectName}"
  def pom = "https://api.bitbucket.org/1.0/repositories/${username}/${projectName}/raw/master/pom.xml";
  def dockerfile = "https://api.bitbucket.org/1.0/repositories/${username}/${projectName}/raw/master/Dockerfile";
  if(!exists(pom) && exists(dockerfile)){
    freeStyleJob("${projectName}") {
      scm {
        git {
            remote {
                url("https://api.bitbucket.org/2.0/repositories/${username}/${projectName}")
            }
            createTag(false)
        }
      }
      steps {
        shell('echo hello')
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
