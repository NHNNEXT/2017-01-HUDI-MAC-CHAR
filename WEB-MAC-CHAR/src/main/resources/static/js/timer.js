export default class Timer {
	constructor() {
		this.leftTime;
		this.calcLeftTime;
		
		this.readyBtn = document.getElementById("readyBtn");
	}
	
	checkTimerStart(canStart) {
		if (canStart) {
			startTimer(5);
			return;
		}
	}
	
	startTimer(time) {
		this.leftTime = time;
		this.calcLeftTime = setInterval(timer, 1000);
		this.readyBtn.removeEventListener("click", readyBtnClickHandler);
		readyBtn.classList.add("timer_started");
	}
	
	timer() {
		document.querySelector(".timer").innerText = timeFormat(this.leftTime--);
		if (this.leftTime < 0) {
			clearInterval(calcLeftTime);
		}
	}
	
	timeFormat(leftTime) {
		let leftMinutes = parseInt(leftTime / 60);
		let leftSeconds = leftTime % 60;
		return `${prependZero(leftMinutes, 2)}:${prependZero(leftSeconds, 2)}`;
	}
	
	prependZero(num, len) {
		while (num.toString().length < len) {
			num = "0" + num;
		}
		return num;
	}
}