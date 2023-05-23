package com.everyparking.server.data.entity;

import com.everyparking.server.data.dto.ReportDto;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.util.Lazy;

@Entity
@Builder
@AllArgsConstructor
public class Report extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    private String title;

    private String contents;

    @OneToMany(cascade = CascadeType.PERSIST )
    @JoinColumn(name = "image_id")
    private List<UploadFile> uploadFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void uploadFile(UploadFile uploadFile) {
        this.uploadFiles.add(uploadFile);
    }

    public Report() {

    }
}
