package com.athena.repository.mongo;

import com.athena.model.CopyDamageReport;
import com.mongodb.gridfs.GridFSFile;

import java.io.InputStream;

/**
 * Created by Tommy on 2017/11/30.
 */
public interface CustomCopyDamageReportRepository {

    GridFSFile getImage(CopyDamageReport copyDamageReport);

    void saveImage(CopyDamageReport copyDamageReport, InputStream inputStream);
}
