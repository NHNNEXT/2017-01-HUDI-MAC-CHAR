export default class voteSocket {
	connect(stompClient, vote_url, destinationUrl) {
		this.stompClient = stompClient;
		this.destinationUrl = destinationUrl;
		stompClient.subscribe(vote_url, vote => {
			
		});
	}
	
	sendVoteResult(userName, theVoted) {
		const data = {
				"userName": userName,
				"theVoted": theVoted
		};
		this.stompClient.send(this.destinationUrl, JSON.stringify(data));
	}
	
	killVictim(theVoted) {
		Array.from(document.getElementsByClassName("slot_layout")).forEach(slot => {
			if (slot.getAttribute('data-id') === theVoted) {
				slot.classList.add("dead");
				slot.getElementsByClassName("player_status")[0].innerHTML = "";
			}
		})
	}
}