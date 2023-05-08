package com.everyparking.server.data.entity;

import javax.persistence.Column;
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

@Entity
@Table(name = "ParkingInfo")
@Getter
@Builder
@AllArgsConstructor
public class ParkingInfo extends BaseTime{


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
}
