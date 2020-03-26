package com.commonsl.json;

import java.util.Date;

public class JsonAnswer {

    String opcode;
    int status ;
    String message ;
    Date datetime;

    public JsonAnswer( String opcode, int status ,String message ){
        this.opcode = opcode;
        this.status = status;
        this.message = message;
        this.datetime = new Date();
    }
    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
} 