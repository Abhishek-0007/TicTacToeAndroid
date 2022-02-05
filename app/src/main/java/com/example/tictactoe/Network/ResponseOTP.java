package com.example.tictactoe.Network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseOTP {
    @SerializedName("otp")
    @Expose
    private int otp;
    @SerializedName("portal")
    @Expose
    private String portal;

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }
}
