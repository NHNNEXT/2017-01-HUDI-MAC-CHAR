package com.zimincom.mafiaonline.item;

/**
 * Created by Zimincom on 2017. 5. 21..
 */

public class VoteMessage {
    String userName;
    String theVoted;
    String stage;

    public VoteMessage() {}

    public VoteMessage(String userName, String theVoted, String stage) {
        this.userName = userName;
        this.theVoted = theVoted;
        this.stage = stage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
    public String getTheVoted() {
        return theVoted;
    }

    public void setTheVoted(String theVoted) {
        this.theVoted = theVoted;
    }

    @Override
    public String toString() {
        return "VoteMessage{" +
                "userName='" + userName + '\'' +
                ", theVoted='" + theVoted + '\'' +
                '}';
    }
}
