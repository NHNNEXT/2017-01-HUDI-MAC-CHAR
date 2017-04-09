window.onload = () => {
	init();
}

function init() {
	let dimmer = document.querySelector("#dimmer");
	let rooms = document.getElementsByClassName("room");
	
	document.querySelector("#make_room").addEventListener("click", () => {
		dimmer.style.display = "block";
		document.querySelector("#make_room_form").style.display = "block";
	});

	dimmer.addEventListener("click", function() {
		dimmer.style.display = "none";
		offModal();
	});
	
	
	for (eachRoom of rooms) {
		eachRoom.addEventListener("click", function(e) { 
			window.location.href = "/room/" + e.target.parentElement.id;
			});
	}
	
	function offModal() {
		let modals = document.querySelectorAll(".my_modal");
		console.log(modals);
		for (modal of modals) {
			modal.style.display = "none";
		}
	}
}
