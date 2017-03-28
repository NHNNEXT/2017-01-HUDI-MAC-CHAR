$(document).ready(function() {
	$('#make_room').on("click", function() {
		$('#dimmer').prop("style").display = "block";
		$('#make_room_form').prop("style").display = "block";
	});

	$('#dimmer').on("click", function() {
		$(this).prop("style").display = "none";
		$('.my_modal').prop("style").display = "none";
	});
});