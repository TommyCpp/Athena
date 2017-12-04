package com.athena.repository.mongo;

import com.athena.model.CopyDamageReport;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.io.InputStream;
import java.util.Objects;

/**
 * Created by Tommy on 2017/11/30.
 */
@Component
public class CopyDamageReportRepositoryImpl implements CustomCopyDamageReportRepository {

    private GridFsTemplate gridFsTemplate;

    @Autowired
    public CopyDamageReportRepositoryImpl(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }


    @Override
    public GridFSDBFile getImage(CopyDamageReport copyDamageReport) {
        String id = copyDamageReport.getImageId();
        Objects.requireNonNull(id);
        return this.gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));//todo:test
    }

    @Override
    public void saveImage(CopyDamageReport copyDamageReport, InputStream inputStream, MimeType mimeType) {
        if (mimeType.equals(MimeTypeUtils.IMAGE_GIF) || mimeType.equals(MimeTypeUtils.IMAGE_JPEG) || mimeType.equals(MimeTypeUtils.IMAGE_PNG)) {
            //if the mimeType is image
            String filename = copyDamageReport.getId();
            DBObject metaData = new BasicDBObject();
            GridFSFile gridFsFile = this.gridFsTemplate.store(inputStream, filename, mimeType.toString());
            //save to copyDamageReport
            copyDamageReport.setImageId(gridFsFile.getId().toString());
        }
    }
}