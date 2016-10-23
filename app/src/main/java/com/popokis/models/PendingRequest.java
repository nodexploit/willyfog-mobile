package com.popokis.models;

import java.util.List;

public class PendingRequest {

    List<UserRequests> pending;

    public PendingRequest() {}

    public List<UserRequests> getPending() {
        return pending;
    }

    public void setPending(List<UserRequests> pending) {
        this.pending = pending;
    }
}
