package com.everyparking.server.data.repository;

import com.everyparking.server.data.entity.ParkingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingInfoRepository extends JpaRepository<ParkingInfo, Long> {

}
