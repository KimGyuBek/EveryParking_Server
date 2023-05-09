package com.everyparking.server.data.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/*TODO Entity에서의 setter 제한*/
@Entity
//@Data
@Table(name = "Member")
@Builder
@AllArgsConstructor
@Getter
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userId;

    private String password;

    private String userName;


    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    @Embedded
    private UserInfo userInfo;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private List<Message> messageList = new ArrayList<>();


    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parkingInfo_id")
    private ParkingInfo parkingInfo;

    public Member() {

    }

    /*TODO 소셜 로그인을 위한 변수 추가*/
}
