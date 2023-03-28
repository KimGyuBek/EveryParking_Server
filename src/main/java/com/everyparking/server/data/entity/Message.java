package com.everyparking.server.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Message extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private String details;

//    private UploadFile uploadFile;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member sender;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Member receiver;
}
