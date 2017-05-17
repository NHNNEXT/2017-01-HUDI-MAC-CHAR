import connect from "./js/connect";

import chatSocket from "./js/chatSocket";
import readySocket from "./js/readySocket";
import accessSocket from "./js/accessSocket";
import gameStartSocket from "./js/gameStartSocket";
import voteSocket from "./js/voteSocket";
import dayTime from "./js/dayTime";
import nightTime from "./js/nightTime";

(function () {
	const VoteSocket = new voteSocket();
	const DayTime = new dayTime();
	const NightTime = new nightTime(VoteSocket);
    const GameStartSocket = new gameStartSocket(DayTime, NightTime, VoteSocket);

    new connect(
        new chatSocket(),
        new readySocket(GameStartSocket),
        new accessSocket(),
        GameStartSocket,
        VoteSocket
    ).init();
})();
