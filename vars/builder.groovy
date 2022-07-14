def buildSource(){
	dir(SOURCE_DIR){
		stage("Update Repo"){
			echo "Update Repo"
			gitlab.updateRepo()
		}
		stage("Build Source Code"){
			echo "Build Source Code"
		}
	}
}