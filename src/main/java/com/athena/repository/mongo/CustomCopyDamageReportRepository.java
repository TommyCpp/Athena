package com.athena.repository.mongo;

import com.athena.model.CopyDamageReport;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.util.MimeType;

import java.io.InputStream;

/**
 * Created by Tommy on 2017/11/30.
 */
public interface CustomCopyDamageReportRepository {

    GridFSDBFile getImage(CopyDamageReport copyDamageReport);

    void saveImage(CopyDamageReport copyDamageReport, InputStream inputStream, MimeType mimeType);
}
