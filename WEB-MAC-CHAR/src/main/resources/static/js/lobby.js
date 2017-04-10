window.onload = () => {
	init();
}

function init() {
	let dimmer = document.querySelector("#dimmer");

	document.querySelector("#make_room").addEventListener("click", () => {
		dimmer.style.display = "block";
		document.querySelector("#make_room_form").style.display = "block";
	});

	dimmer.addEventListener("click", function() {
		dimmer.style.display = "none";
		offModal();
	});

    document.querySelector(".rooms_list").addEventListener("click", forwardEachRoom);

    function forwardEachRoom(e) {
        let target = e.target;
        if (target.tagName.toLowerCase() === "td") {
            window.location.href = "/room/" + target.parentNode.id;
        }
	}
	
	function offModal() {
		let modals = document.querySelectorAll(".my_modal");
		console.log(modals);
		for (modal of modals) {
			modal.style.display = "none";
		}
	}
}
