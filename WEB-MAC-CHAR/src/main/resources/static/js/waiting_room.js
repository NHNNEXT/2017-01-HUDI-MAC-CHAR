window.onload = function() {
	init();
}

function init() {
	document.getElementById("make_room").addEventListener("click", function() {
		document.getElementById("dimmer").style.display = "block";
		document.getElementById("make_room_form").style.display = "block";
	});

	document.getElementById("dimmer").addEventListener("click", function() {
		this.style.display = "none";
		offModal();
	});

	function offModal() {
		let modals = document.getElementsByClassName("my_modal");
		for (let i = 0; i < modals.length; i++) {
			modals[i].style.display = "none";
		}
	}
}


