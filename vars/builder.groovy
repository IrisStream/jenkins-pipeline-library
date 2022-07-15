def buildSource(SOURCE_DIR){
	dir(SOURCE_DIR){
		stage("Update Repo"){
			echo "Update Repo"
			gitlab.updateRepo()
		}
		stage("Build Source Code"){
			echo "Build Source Code"
			sh "$WORKSPACE/jenkins_scripts/build_src.sh"
		}
	}
}