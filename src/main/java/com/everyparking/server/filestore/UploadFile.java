package com.everyparking.server.filestore;

import com.everyparking.server.data.entity.BaseTime;
import com.everyparking.server.data.entity.Member;
import com.everyparking.server.data.entity.Report;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "UploadFile")
@Getter
public class UploadFile extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String uploadFileName;

    private String storeFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Report report;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
//        this.report = report;

    }


    public UploadFile() {

    }
}
