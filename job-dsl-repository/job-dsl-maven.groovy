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
    def pomUrl = new URL(pom)
    def pomRootNode = new XmlSlurper().parse(pomUrl.newReader())
    def mavenPackaging = pomRootNode.packaging.text()
    mavenJob("${projectName}") {
      scm {
        git {
            remote {
                url("https://bitbucket.org/${username}/${projectName}")
            }
            createTag(false)
        }
      }
      properties{
        promotions {
          promotion {
            name('dev')
            conditions {
              manual('tester')
            }
            actions {
              shell('echo deploy it!;')
            }
          }
          promotion {
            name('staging')
            conditions {
              manual('tester')
            }
            actions {
              shell('echo deploy it!;')
            }
          }
        }
      }
      goals("clean package")
      wrappers {
  			preBuildCleanup()
  			release {
  				preBuildSteps {
  					maven {
  						goals("build-helper:parse-version")
  						goals("versions:set")
  						property("newVersion", "\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.incrementalVersion}")
  					}
  				}
  				postSuccessfulBuildSteps {
  					maven {
  						rootPOM("${projectName}/pom.xml")
              goals("deploy -Pdocker -DpushImage")
              //TODO goals("deploy -Prpm")
              //TODO altDeploymentRepository=nexus-releases-repository::default::http://nexus:8081/content/repositories/releases/
  					}
  				}
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
