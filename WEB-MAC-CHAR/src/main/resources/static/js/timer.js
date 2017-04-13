let timer = (function(){
	
	function init() {
		let dayTime = 180;
		let mafiaTime = 10;
		let policeTime = 10;
		let doctorTime = 10;
		
		let leftTime;
		let calcLeftTime;

		document.querySelector("#timeZone").addEventListener("click", (e) => {
			if(e.target.tagName == "BUTTON"){
				startTimer(e.target.innerText);
			}
		});
		
		function startTimer(time){
			leftTime = time;
			calcLeftTime = setInterval(timer, 1000);
			
		}
		
		function timer() {
			document.querySelector(".timer").innerText = leftTime--;
			if(leftTime < 0) {
				clearInterval(calcLeftTime);
			}
		}
		
	}
	
	return {
		init: init
	}
})(); // self executing function

(function(){
	timer.init();
})();