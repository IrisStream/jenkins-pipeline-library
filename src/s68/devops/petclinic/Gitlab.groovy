class Gitlab{
	def updateRepo(){
		if(isMergeRequest()){
			checkout changelog: true, poll: true, scm: [
				$class: 'GitSCM',
				branches: [[name: "origin/${gitlabSourcuBranch}"]],
				extensions: [
					[
						$class: 'PreBuildMerge', 
						options: [
							fastForwardMode: 'FF', 
							mergeRemote: 'origin', 
							mergeStrategy: 'DEFAULT', 
							mergeTarget: "${gitlabTargetBranch}"
						]
					]
				],
				userRemoteConfigs: [[name: 'origin', url: "${gitlabSourceRepoSshUrl}"]]
			]
		}
		else{
			checkout changelog: true,poll: true, scm: [
				$class: 'GitSCM',
				branches: [[ name: "${gitlabAfter}" ]],
				userRemoteConfigs: [[name: 'origin', url: "${gitlabSourceRepoSshUrl}"]],
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

	def getMergeRequestByFullName(){
		gitlabUserName
	}
}