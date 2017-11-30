package com.athena.repository.mongo;

import com.athena.model.CopyDamageReport;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Created by Tommy on 2017/11/30.
 */
@Component
public class CopyDamageReportRepositoryImpl implements CustomCopyDamageReportRepository {

    private final GridFsTemplate template;

    @Autowired
    public CopyDamageReportRepositoryImpl(GridFsTemplate gridFsTemplate) {
        this.template = gridFsTemplate;
    }


    @Override
    public GridFSFile getImage(CopyDamageReport copyDamageReport) {
        return null; //todo: save and load image
    }

    @Override
    public void saveImage(CopyDamageReport copyDamageReport, InputStream inputStream) {

    }
}