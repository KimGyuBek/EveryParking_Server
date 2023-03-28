package com.everyparking.server.data.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ParkingLot extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parkingLot_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parkingLot")
    private List<ParkingInfo> parkingInfoList;

    private int total;

    private int available;

    private int used;

}
