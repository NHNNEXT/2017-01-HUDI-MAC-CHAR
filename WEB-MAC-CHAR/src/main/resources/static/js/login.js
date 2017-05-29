let login = (function () {
    let button = document.querySelector("#login_button");
    let [emailInput, passwordInput] = document.querySelectorAll("[name]"); //Destructuring assignment
    const roomPage = "/lobby";

    function tryLogin(e) {
        e.preventDefault();

        let url = document.querySelector("#login_form").getAttribute("action");
        let jsonString = JSON.stringify({
            "email": emailInput.value,
            "password": passwordInput.value
        });

        $.ajax({
            type: "post",
            url: url,
            data: jsonString,
            contentType: "application/json",
            error: onError,
            success: onSuccess
        });
    }

    function onSuccess(data, status) {
        if (data.status === "Ok") {
            location.href = roomPage;
        }

        if (data.status === "EmailNotFound") {
            userNotify(data.msg);
        }

        if (data.status === "InvalidPassword") {
            userNotify(data.msg);
            passwordInput.value = "";
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

(function () {
    login.init();
})();
