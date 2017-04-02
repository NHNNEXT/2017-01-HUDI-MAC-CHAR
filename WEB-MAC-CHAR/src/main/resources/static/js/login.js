let login = (function(){
	let button = document.querySelector("#login_button");
	const roomPage = "/room";
	
	function tryLogin(e) {
		e.preventDefault();
		
		let url = document.querySelector("#login_form").getAttribute("action");
		let userInputs = document.querySelectorAll("[name]");
		let jsonString = JSON.stringify({"email" : userInputs[0].value,
										 "password" : userInputs[1].value});
		
		console.log("url : " + url);
		console.log("jsonString : " + jsonString);
		
		$.ajax({
			type: "post",
			url: url,
			data : jsonString,
			contentType: "application/json",
			error: onError,
			success: onSuccess
		});
	}

	function onSuccess(data, status) {
		console.log(data);
		
		if (data.statusCode === "Ok") {
			location.href = roomPage;
		}
		
		if (data.statusCode === "EmailNotFound") {
			userNotify(data.errorMessage);
		}
		
		if (data.statusCode === "InvalidPassword") {
			userNotify(data.errorMessage);
		}
	}
	
	function onError(xhr, status) {
		alert("error");
	}
	
	// 로그인 실패시 사용자에게 원인을 알려준다. Signup.js에도 같은 함수가 있는데 어떻게 뺄 수 있을까...
	function userNotify(errorMessage) {
		let notify = document.querySelector("#error_report");
		notify.textContent = errorMessage;
	}
	
	function init() {
		button.addEventListener("click", tryLogin);
	}
	
	return {
		init: init
	}
})(); // self executing function

(function(){
	login.init();
})();