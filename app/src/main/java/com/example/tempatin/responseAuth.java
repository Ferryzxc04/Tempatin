package com.example.tempatin;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;


public class responseAuth {
    @SerializedName("status")
    String status;
    @SerializedName("pesan")
    JsonObject pesan;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JsonObject getPesan() {
        return pesan;
    }

    public void setPesan(JsonObject pesan) {
        this.pesan = pesan;
    }
}
