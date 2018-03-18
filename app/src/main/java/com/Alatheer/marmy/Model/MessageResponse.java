package com.Alatheer.marmy.Model;

import java.io.Serializable;

/**
 * Created by m on 3/15/2018.
 */

public class MessageResponse implements Serializable {
    private String message;
    private int success;

    public MessageResponse() {
    }

    public MessageResponse(String message, int success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
