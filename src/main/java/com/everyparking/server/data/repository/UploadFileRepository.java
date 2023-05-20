package com.everyparking.server.data.repository;

import com.everyparking.server.filestore.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {


}
