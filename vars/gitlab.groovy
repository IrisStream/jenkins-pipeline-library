def updateRepo(){
	withCredentials([string(credentialsId: 'git-credential', variable: 'GIT_CREDENTIAL']){
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
				userRemoteConfigs: [[
					name: 'origin', 
					url: "${env.gitlabSourceRepoSshUrl}",
					credentialsId: 'git-credential'
				]]
			]
		}
		else{
			checkout changelog: true,poll: true, scm: [
				$class: 'GitSCM',
				branches: [[ name: "${env.gitlabAfter}" ]],
				userRemoteConfigs: [[
					name: 'origin', 
					url: "${env.gitlabSourceRepoSshUrl}",
					credentialsId: 'git-credential'
				]]
			]
		}
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
	env.gitlabAfter
}