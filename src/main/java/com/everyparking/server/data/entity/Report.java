package com.everyparking.server.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity

/*TODO Message.class와 겹치는 부분이 많음 -> 합치는게 좋을 듯*/
public class Report extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    private String detail;

//    private UploadFile uploadFile;

//    @ManyToOne
//    private Member sender;
}
