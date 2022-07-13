class Gitlab{
	def updateRepo(){
		if(isMergeRequest()){
			checkout changelog: true, poll: true, scm: [
				$class: 'GitSCM',
				branches: [[name: "origin/${getSourceBranch()}"]],
				extensions: [
					[
						$class: 'PreBuildMerge', 
						options: [
							fastForwardMode: 'FF', 
							mergeRemote: 'origin', 
							mergeStrategy: 'DEFAULT', 
							mergeTarget: "${getTargetBranch()}"
						]
					]
				],
				userRemoteConfigs: [[name: 'origin', url: "${getSourceUrl()}"]]
			]
		}
		else{
			checkout changelog: true,poll: true, scm: [
				$class: 'GitSCM',
				branches: [[ name: "${getCommitId()}" ]],
				userRemoteConfigs: [[name: 'origin', url: "${getSourceRepoUrl()}"]],
			]
		}
	}

	def isMergeRequest(){
		try {
			gitlabMergeRequestIId
			true
		} catch (e) {
			return false
		}
	}

	def getMergeRequestInfo(){
		"PR-${getMergeRequestId()}, branch: ${(getSourceBranch())} by ${getMergeRequestByFullName()}\n${getMergeRequestUrl()}"
	}

	def getMergeRequestUrl(){
		"${gitlabTargetRepoHttpUrl.minus('.git')}/merge_requests/$gitlabMergeRequestIId"
	}

	def getMergeRequestId(){
		gitlabMergeRequestIId
	}

	def getSourceBranch(){
		gitlabSourceBranch
	}

	def getTargetBranch(){
		gitlabTargetBranch
	}

	def getMergeRequestByFullName(){
		gitlabUserName
	}

	def getRepoUrl(){
		gitlabSourceRepoHttpUrl
	}
	
	def getCommitId(){
		gitlabAfter
	}
}