class Notify{
	def SuccessMessage = "Congratulations, you have successfully registered a new pet!"
	def FailureMessage = "Sorry, something went wrong. Please try again."
	def notify(String result){
		if(result.equals("SUCCESS")){
			return sendTelegram(SuccessMessage);
		}
		else if(result.equals("FAILURE")){
			return sendTelegram(FailureMessage);
		}
		else if(result.equals("ABORTED")){
			return "ABORTED";
		}
		else{
			return "UNKNOWN";
		}
	}

	def sendTelegram(String message){
		def encodedMessage = URLEncoder.encode(message, "UTF-8")
		withCredentials([string(credentialsId: 'telegramToken', variable: 'TOKEN'),
		string(credentialsId: 'telegramChatId', variable: 'CHAT_ID')]) {
			response = httpRequest (consoleLogResponseBody: true,
				contentType: 'APPLICATION_JSON',
				httpMode: 'GET',
				url: "https://api.telegram.org/bot$TOKEN/sendMessage?text=$encodedMessage&chat_id=$CHAT_ID&parse_mode=html&disable_web_page_preview=true",
				validResponseCodes: '200')
			return response
		}
	}
}