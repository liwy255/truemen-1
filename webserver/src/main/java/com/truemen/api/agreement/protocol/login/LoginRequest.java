package com.truemen.api.agreement.protocol.login;

import com.truemen.api.agreement.protocol.Command;
import com.truemen.api.agreement.protocol.Packet;

/**
 */
public class LoginRequest extends Packet {

    private String userId; // 用户ID
    private String userPassword; // 用户密码

    public LoginRequest(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public Byte getCommand() {
        return Command.LoginRequest;
    }

}
