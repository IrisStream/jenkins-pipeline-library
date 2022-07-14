def testSource(){
	stage("Code Quality Check"){
		echo "Code Quality Check"
		dir(SOURCE_DIR){
			withSonarQubeEnv(installationName: 'spring-petclinic'){
				echo "Quality Check"
				echo "$JAVA_HOME"
				echo "mvn --version"
				sh "$WORKSPACE/jenkins_scripts/quality_check.sh"
			}
		}
	}
	stage("Quality Gate"){
		echo "Quality Gate"
		timeout(time: 20, unit: "MINUTES"){ 
			waitForQualityGate abortPipeline: true
		}
	}
	stage("Unit Test"){
		echo "Unit Test"
		sh "./jenkins_scripts/unit_test.sh"
	}
}