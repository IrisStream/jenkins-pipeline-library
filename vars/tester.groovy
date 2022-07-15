def testSource(SOURCE_DIR){
	stage("Code Quality Check"){
		echo "Code Quality Check"
		dir(SOURCE_DIR){
			withSonarQubeEnv(installationName: 'spring-petclinic'){
				echo "Quality Check"
				sh "$WORKSPACE/jenkins_scripts/quality_check.sh ${SOURCE_DIR}"
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
		sh "./jenkins_scripts/unit_test.sh ${SOURCE_DIR}"
	}
}