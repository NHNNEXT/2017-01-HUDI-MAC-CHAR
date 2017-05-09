history.pushState(null, null, location.href);
window.onpopstate = function(event) {
	history.go(1);
}
