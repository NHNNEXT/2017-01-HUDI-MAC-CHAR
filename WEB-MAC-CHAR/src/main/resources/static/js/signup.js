let signup = (function(){
	const loginPage = "/login";
	const EMPTY_VALUE_NOTIFY = "비어 있는 항목이 있습니다.";
	const INVALID_EMAIL_FORM = "올바르지 않은 이메일 형식입니다.";
	const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	let button = document.querySelector("#signup_button");
	let userInputs = document.querySelectorAll("[name]");
	
	function passwordConfirm() {
		let password = document.querySelector("#password").value;
		let passwordConfirm = document.querySelector("#password_confirm").value;
		password === passwordConfirm ? button.disabled = false : button.disabled = true;
	}
	
	function isEmpty() {
		for (input of userInputs) {
			if (input.value == null || input.value == "") {
				return true;
			}
		}
		return false;
	}
	
	function validateEmail(email) {
	    return re.test(email);
	}
	
	function trySignup(e) {
		e.preventDefault();
		let email = document.querySelector("input[name=email]").value;		
		
		if (isEmpty()) {
			userNotify(EMPTY_VALUE_NOTIFY);
			return;
		}
		
		if (!validateEmail(email)) {
			userNotify(INVALID_EMAIL_FORM);
			return;
		}
		
		let url = document.querySelector("#signup_form").getAttribute("action");
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
		
		if (data.status === "Ok") {
			location.href = loginPage;
		}
		
		if (data.status === "EmailExists") {
			userNotify(data.msg);
		}
		
		if (data.status === "NicknameExists") {
			userNotify(data.msg);
		}
	}
	
	function onError(xhr, status) {
		alert("error");
	}
	
	function init() {
		let passwordInputs = document.querySelectorAll("input[type=password]");
		for (let inputBox of passwordInputs) {
			inputBox.addEventListener("keyup", passwordConfirm);
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


