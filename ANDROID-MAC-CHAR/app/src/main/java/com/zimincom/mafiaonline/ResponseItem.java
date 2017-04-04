package com.zimincom.mafiaonline;

/**
 * Created by Zimincom on 2017. 4. 3..
 */

public class ResponseItem {

    String statusCode;
    String errorMessage;

    public ResponseItem(String statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ResponseItem{" +
                "statusCode=" + statusCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
