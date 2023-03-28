package com.everyparking.server.data.entity;

import javax.persistence.Embeddable;
import lombok.Data;

@Embeddable

@Data
public class UserInfo {

    private String phoneNumber;
    private String address;
    private String email;


    public UserInfo(String phoneNumber, String address, String email) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

    public UserInfo() {

    }

    //    @Nullable
//    private int studentId;

}
