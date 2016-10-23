package com.popokis.models;

import java.util.List;

public class ClosedRequest {

    List<UserRequests> closed;

    public ClosedRequest() {}

    public List<UserRequests> getClosed() {
        return closed;
    }

    public void setClosed(List<UserRequests> closed) {
        this.closed = closed;
    }
}
