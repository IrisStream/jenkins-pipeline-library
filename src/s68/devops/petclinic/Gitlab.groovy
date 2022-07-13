class Gitlab{
	def updateRepo(){
		if(isMergeRequest()){
			checkout changelog: true, poll: true, scm: [
				$class: 'GitSCM',
				branches: [[name: "origin/${env.gitlabSourceBranch}"]],
				extensions: [
					[
						$class: 'PreBuildMerge', 
						options: [
							fastForwardMode: 'FF', 
							mergeRemote: 'origin', 
							mergeStrategy: 'DEFAULT', 
							mergeTarget: "${env.gitlabTargetBranch}"
						]
					]
				],
				userRemoteConfigs: [[name: 'origin', url: "${env.gitlabSourceRepoSshUrl}"]]
			]
		}
		else{
			checkout changelog: true,poll: true, scm: [
				$class: 'GitSCM',
				branches: [[ name: "${env.gitlabAfter}" ]],
				userRemoteConfigs: [[name: 'origin', url: "${env.gitlabSourceRepoSshUrl}"]],
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