import groovy.json.*
def call(){
    def settings = libraryResource 's68/devops/petclinic/jenkins-settings.json'
    def var = readJSON text: settings
    return var
}