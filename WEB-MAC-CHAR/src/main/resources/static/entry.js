import connect from "./js/connect";

import chatSocket from "./js/chatSocket";
import readySocket from "./js/readySocket";
import accessSocket from "./js/accessSocket";

(function() {
    new connect(
        new chatSocket(),
        new readySocket(),
        new accessSocket()
    ).init();
})();
