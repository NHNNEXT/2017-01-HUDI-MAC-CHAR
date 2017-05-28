import connect from "./js/connect";

import chatSocket from "./js/chatSocket";
import readySocket from "./js/readySocket";
import accessSocket from "./js/accessSocket";
import gameStartSocket from "./js/gameStartSocket";
import voteSocket from "./js/voteSocket";
import dayTime from "./js/dayTime";
import nightTime from "./js/nightTime";
import investSocket from "./js/investSocket";

(function () {
    const VoteSocket = new voteSocket();
    const InvestSocket = new investSocket();
    const DayTime = new dayTime(VoteSocket);
    const NightTime = new nightTime(VoteSocket, InvestSocket);
    const GameStartSocket = new gameStartSocket(VoteSocket, DayTime, NightTime);

    new connect(
        new chatSocket(),
        new readySocket(GameStartSocket),
        new accessSocket(),
        GameStartSocket,
        VoteSocket,
        InvestSocket
    ).init();
})();
