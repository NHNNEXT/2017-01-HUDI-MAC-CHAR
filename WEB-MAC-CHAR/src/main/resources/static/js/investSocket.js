import {printMessage} from "./room_util";

export default class investSocket {
	connect(stompClient, invest_url, destinationUrl) {
		
		this.stompClient = stompClient;
		this.destinationUrl = destinationUrl;
		
		stompClient.subscribe(invest_url, result => {
			printMessage(`그 플레이어의 직업은 ${result.body} 입니다.`);
		})
	}
	
	sendInvest(theVoted) {
		const data = {
			"theVoted": theVoted
		}
		this.stompClient.send(this.destinationUrl, JSON.stringify(theVoted));
	}
}