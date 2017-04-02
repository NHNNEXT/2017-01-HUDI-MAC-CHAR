let signup = (function(){
	let button = document.querySelector("#signup_button");
	const loginPage = "/login";
	
	function validation() {
		let password = document.querySelector("#password").value;
		let passwordConfirm = document.querySelector("#password_confirm").value;
		password === passwordConfirm ? button.disabled = false : button.disabled = true;
	}
	
	function trySignup(e) {
		e.preventDefault();
		
		let url = document.querySelector("#signup_form").getAttribute("action");
		let userInputs = document.querySelectorAll("[name]");
		let jsonString = JSON.stringify({"email" : userInputs[0].value,
										 "password" : userInputs[1].value,
										 "nickname" : userInputs[2].value});
		
		$.ajax({
			type: "post",
			url: url,
			data : jsonString,
			contentType: "application/json",
			error: onError,
			success: onSuccess
		});
	}
	
	function userNotify(errorMessage) {
		let notify = document.querySelector("#error_report");
		notify.textContent = errorMessage;
	}

	function onSuccess(data, status) {
		console.log(data);
		
		if (data.statusCode === "Ok") {
			location.href = loginPage;
		}
		
		if (data.statusCode === "EmailExists") {
			userNotify(data.errorMessage);
		}
	}
	
	function onError(xhr, status) {
		alert("error");
	}
	
	function init() {
		let passwordInputs = document.querySelectorAll("input[type=password]");
		for (let inputBox of passwordInputs) {
			inputBox.addEventListener("keyup", validation);
		}
		button.addEventListener("click", trySignup);
	}
	
	return {
		init: init
	}
})(); // self executing function

(function(){
	signup.init();
})();


