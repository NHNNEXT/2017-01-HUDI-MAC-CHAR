window.onload = () => {
	init();
}

function init() {
	let dimmer = document.querySelector("#dimmer");
	let eachRoom = document.querySelector(".eachRoom");
	
	document.querySelector("#make_room").addEventListener("click", () => {
		dimmer.style.display = "block";
		document.querySelector("#make_room_form").style.display = "block";
	});

	dimmer.addEventListener("click", function() {
		dimmer.style.display = "none";
		offModal();
	});
	
	eachRoom.addEventListener("click", function() {
		window.location.href = "/room/" + this.id;
	});
	
	function offModal() {
		let modals = document.querySelectorAll(".my_modal");
		console.log(modals);
		for (modal of modals) {
			modal.style.display = "none";
		}
	}
}
