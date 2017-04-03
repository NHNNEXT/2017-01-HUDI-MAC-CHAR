window.onload = () => {
	init();
}

function init() {
	document.querySelector("#make_room").addEventListener("click", () => {
		document.querySelector("#dimmer").style.display = "block";
		document.querySelector("#make_room_form").style.display = "block";
	});

	document.querySelector("#dimmer").addEventListener("click", () => {
		document.querySelector("#dimmer").style.display = "none";
		offModal();
	});

	function offModal() {
		var modals = document.querySelectorAll(".my_modal");
		console.log(modals);
		for (modal of modals) {
			modal.style.display = "none";
		}
//		[].forEach.call(modals, (modal) => {
//			modal.style.display = "none";
//		});
	}
}


