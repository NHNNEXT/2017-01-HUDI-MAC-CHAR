import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';
import Util from './room_util';

export default class socket {
	constructor() {
		this.SERVER_SOCKET_API = "/websockethandler";
		this.FROM_ACCESS_API = "/from/access/";
		this.TO_ACCESS_API = "/to/access/";
		
		this.PLAYER_NOT_READY = "player_not_ready";
		
		this.textArea = document.getElementById("chatOutput");
		
		this.stompClient;
	}
	
	init() {
        document.addEventListener("DOMContentLoaded", this.connect.bind(this));
	}
	
	connect() {
		console.log(this);
		let socket = new SockJS(this.SERVER_SOCKET_API);
		let access_url = this.FROM_ACCESS_API + Util.getRoomId();
		
		this.stompClient = Stomp.over(socket);
		console.log(this.stompClient);
		this.stompClient.connect({}, () => {
			this.stompClient.subscribe(access_url, access => {
				this.showUserList(JSON.parse(access.body));
			})
		})
	}
	
	sendAccess(userName, access) {
		let destinationUrl = this.TO_ACCESS_API + Util.getRoomId();
		let msg = {
				"userName": userName,
				"access": access
		};
		console.log(msg);
		this.stompClient.send(destinationUrl, JSON.stringify(msg));
	}
	
	showUserList(access) {
		if (access.access === "enter") {
			let slots = document.querySelectorAll(".slot_layout");
			for (let slot of slots) {
				slot.querySelector(".player_name").textContent = "";
				slot.querySelector(".player_status").classList.add(this.PLAYER_NOT_READY);
				slot.removeAttribute("data-id");
				slot.classList.add("empty_slot");
			}
			for (let user of access.users) {
				let emptySlot = document.querySelector(".empty_slot");
				emptySlot.querySelector(".player_name").textContent = user.nickname;
				if (user.status === "READY") {
					emptySlot.querySelector(".player_status").classList.remove(this.PLAYER_NOT_READY);
				}
				emptySlot.setAttribute("data-id", user.nickname);
				emptySlot.classList.remove("empty_slot");
			}
			Util.syncScroll();
			let msg = `${access.userName} 님이 입장하셨습니다.\n`;
			textArea.value += msg;
		}
	}
}