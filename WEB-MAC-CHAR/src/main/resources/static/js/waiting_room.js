window.onload = () => {
	init();
}

function init() {
	var dimmer = document.querySelector("#dimmer");
	document.querySelector("#make_room").addEventListener("click", () => {
		dimmer.style.display = "block";
		document.querySelector("#make_room_form").style.display = "block";
	});

	dimmer.addEventListener("click", () => {
		dimmer.style.display = "none";
		offModal();
	});

	function offModal() {
		var modals = document.querySelectorAll(".my_modal");
		console.log(modals);
		for (modal of modals) {
			modal.style.display = "none";
		}
	}
}


