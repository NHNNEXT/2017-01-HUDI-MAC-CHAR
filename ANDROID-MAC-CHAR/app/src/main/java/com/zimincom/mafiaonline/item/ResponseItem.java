package com.zimincom.mafiaonline.item;

/**
 * Created by Zimincom on 2017. 4. 3..
 */

public class ResponseItem {

    public String status;
    public String msg;

    public ResponseItem(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }



    @Override
    public String toString() {
        return "ResponseItem{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
