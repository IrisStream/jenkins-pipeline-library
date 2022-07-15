def vars
def call(){
    def settings = libraryResource 's68/devops/petclinic/jenkins-settings.json'
    var = readJSON text: settings
}