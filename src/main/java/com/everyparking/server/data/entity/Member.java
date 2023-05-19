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

    private int studentId;

    /*TODO 사용자 위약 상태 저장*/

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

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

    /*차량 등록 로직*/
    public void registerCar(Car car) {
        this.car = car;
    }

    /*사용자 위약 검증*/
    public boolean checkMemberStatus() {
        if (this.memberStatus == MemberStatus.FORBIDDEN) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkParkingStatus() {
        if (this.parkingInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    /*자리 배정*/
//    public void assignParking(ParkingInfo parkingInfo) {
//        this.parkingInfo = parkingInfo;
//        parkingInfo.assign(this.car);
//    }
//
//    public void returnParking(ParkingInfo parkingInfo) {
//        this.parkingInfo = parkingInfo;
//        parkingInfo.returnParking();
//    }

    public void changeParkingStatus(ParkingInfo parkingInfo) {
        if (this.parkingInfo == null) {
            this.parkingInfo = parkingInfo;

        } else if (this.parkingInfo != null) {
            this.parkingInfo = null;

        }

        parkingInfo.changeParkingStatus();
    }

    /*TODO 소셜 로그인을 위한 변수 추가*/
}
