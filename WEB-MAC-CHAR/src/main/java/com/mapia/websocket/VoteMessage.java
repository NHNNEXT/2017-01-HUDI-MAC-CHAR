package com.mapia.websocket;

public class VoteMessage {
    String userName;
    String theVoted;
    
    public VoteMessage(){}
    
    public VoteMessage(String userName, String theVoted) {
        this.userName = userName;
        this.theVoted = theVoted;
    }

    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getTheVoted() {
        return theVoted;
    }
    
    public void setTheVoted(String theVoted) {
        this.theVoted = theVoted;
    }
    
    @Override
    public String toString() {
        return "VoteMessage [userName=" + userName + ", theVoted=" + theVoted + "]";
    }
}
