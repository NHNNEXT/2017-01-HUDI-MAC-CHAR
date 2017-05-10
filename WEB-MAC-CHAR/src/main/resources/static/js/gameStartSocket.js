import {textArea, syncScroll} from './room_util';

export default class gameStartSocket {
    constructor() {
        this.ENTER_KEY = 13;
        
        this.userName = document.getElementById("userName").textContent;
        
    }

    init() {
    	
    }
    
    connect(stompClient, gameStart_url, destinationUrl) {
    	console.log('DESTINATION URL: '+ destinationUrl);
        this.stompClient = stompClient;
        this.destinationUrl = destinationUrl;
        stompClient.subscribe(gameStart_url, gameStart => {
        });
    }
}

export function	sendGameStart() {
	let msg = {
			"userName": this.userName,
	};
	console.log(msg);
	this.stompClient.send(this.destinationUrl, JSON.stringify(msg));
}