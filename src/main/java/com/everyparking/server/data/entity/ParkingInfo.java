package com.everyparking.server.data.entity;

import com.everyparking.server.data.dto.ParkingDto.MyParkingStatus;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "ParkingInfo")
@Getter
@Builder
@AllArgsConstructor
@Embeddable
@Slf4j
public class ParkingInfo extends BaseTime {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parkingInfo_id")
    private Long id;

    private int parkingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @Enumerated(value = EnumType.STRING)
    private ParkingStatus parkingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parkingLot_id")
    private ParkingLot parkingLot;


    @OneToOne(mappedBy = "parkingInfo")
    private Member member;


    public ParkingInfo() {

    }

    public MyParkingStatus toDto() {
        log.info("[{}]", this.getClass().getName());

        MyParkingStatus result = MyParkingStatus.builder()
            .parkingId(this.getParkingId())
            .remain(0)
            .carNumber(this.getCar().getCarNumber())
            .build();

        return result;

    }

    /*자리 사용중 처리*/
//    public void assign(Car car) {
//        if (this.parkingStatus == ParkingStatus.AVAILABLE) {
//            this.parkingStatus = ParkingStatus.USED;
//            this.car = car;
//        }
//
//    }
//
//    public void returnParking() {
//        if (this.parkingStatus == ParkingStatus.USED) {
//            this.parkingStatus = ParkingStatus.AVAILABLE;
//            this.car = null;
//        }
//    }

    /*parkingStatus 변경*/
    public void changeParkingStatus() {
//        이용가능한 경우 사용중으로 변환
        if (this.parkingStatus == ParkingStatus.AVAILABLE) {
            this.parkingStatus = ParkingStatus.USED;
//            사용중인 경우 이용가능으로 변환
        } else if (this.parkingStatus == ParkingStatus.USED) {
            this.parkingStatus = ParkingStatus.AVAILABLE;
        }
    }
}
