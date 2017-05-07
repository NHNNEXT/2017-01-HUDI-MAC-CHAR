export default class Util {
	constructor() {
		
	}
	
	getRoomId() {
		let roomId = document.getElementById("roomId").textContent;
		let parsed = roomId.split(".");
		return parsed[1];
	}
	
	syncScroll() {
		this.textArea.scrollTop = this.textArea.scrollHeight;
	}
}