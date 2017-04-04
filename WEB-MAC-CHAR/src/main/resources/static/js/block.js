history.pushState(null, null, location.href);
window.onpopstate = function(event) {
	history.go(1);
	alert('뒤로가기는 불가능합니다.');
}