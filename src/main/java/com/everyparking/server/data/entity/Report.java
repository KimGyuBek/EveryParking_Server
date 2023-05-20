package com.everyparking.server.data.entity;

import com.everyparking.server.filestore.UploadFile;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.data.util.Lazy;

@Entity

/*TODO Message.class와 겹치는 부분이 많음 -> 합치는게 좋을 듯*/
public class Report extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    private String detail;

    /*TODO file upload 구현*/
//    @OneToMany(cascade = CascadeType.PERSIST )
//    @JoinColumn(name = "image_id")
//    private List<UploadFile> uploadFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
