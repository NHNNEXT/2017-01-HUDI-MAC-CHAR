package com.mapia.api;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mapia.domain.Lobby;
import com.mapia.domain.Room;
import com.mapia.utils.HttpSessionUtils;

/**
 * Created by Jbee on 2017. 4. 3..
 */
@RestController
@RequestMapping("/api/lobby")
public class ApiLobbyController {
    private static final Logger log = LoggerFactory.getLogger(ApiLobbyController.class);

    @Autowired
    private Lobby lobby;

    @GetMapping("")
    public LobbyResult roomList(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            new LobbyResult("Invalid Access", "Invalid Access", null);
        }
        return new LobbyResult("OK", "Success to get room list", lobby.rooms());
    }

    private class LobbyResult {
        private String status;
        private String msg;
        private Collection<Room> rooms;

        private LobbyResult(String status, String msg, Collection<Room> rooms) {
            this.status = status;
            this.msg = msg;
            this.rooms = rooms;
        }

        public String getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

        public Collection<Room> getRooms() {
            return rooms;
        }
    }
}
