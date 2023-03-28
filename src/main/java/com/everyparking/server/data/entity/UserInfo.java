package com.everyparking.server.data.entity;

import javax.persistence.Embeddable;
import lombok.Data;
import org.springframework.lang.Nullable;

@Embeddable

@Data
public class UserInfo {

    private String phoneNumber;
    private String address;
    private String email;

//    @Nullable
//    private int studentId;

}
