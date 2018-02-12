
package com.athena.repository.mongo;

import com.athena.model.copy.CopyDamageReport;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoGridFSException;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Created by Tommy on 2017/11/30.
 */
@Component
public class CopyDamageReportRepositoryImpl implements CustomCopyDamageReportRepository {

    private GridFsTemplate gridFsTemplate;

    private MongoTemplate mongoTemplate;

    /**
     * Instantiates a new Copy Damage Report repository.
     *
     * @param gridFsTemplate the grid fs template
     */
    @Autowired
    public CopyDamageReportRepositoryImpl(GridFsTemplate gridFsTemplate, MongoTemplate mongoTemplate) {
        this.gridFsTemplate = gridFsTemplate;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public GridFSDBFile getImage(CopyDamageReport copyDamageReport) {
        String id = copyDamageReport.getImageId();
        Objects.requireNonNull(id);
        return this.gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
    }

    @Override
    public CopyDamageReport setImage(CopyDamageReport copyDamageReport, InputStream inputStream, MimeType mimeType) throws IOException {
        if (mimeType.equals(MimeTypeUtils.IMAGE_GIF) || mimeType.equals(MimeTypeUtils.IMAGE_JPEG) || mimeType.equals(MimeTypeUtils.IMAGE_PNG)) {
            //if the mimeType is image
            String filename = copyDamageReport.getId();
            DBObject metaData = new BasicDBObject();
            inputStream.mark(Integer.MAX_VALUE);
            String md5 = DigestUtils.md5DigestAsHex(inputStream);
            inputStream.reset();//reset to the head
            metaData.put("md5", md5);
//            Query if the image is exist;
            Query query = new Query().addCriteria(Criteria.where("metadata.md5").is(md5)).limit(1);
            GridFSDBFile previousImage = this.gridFsTemplate.findOne(query);
            GridFSFile gridFsFile;
            if (Objects.isNull(previousImage)) {
                gridFsFile = this.gridFsTemplate.store(inputStream, filename, mimeType.toString(), metaData);
            } else {
                //use previous image
                gridFsFile = previousImage;
            }
            //save to copyDamageReport
            copyDamageReport.setImageId(gridFsFile.getId().toString());
            return copyDamageReport;
        }
        throw new InvalidMimeTypeException(mimeType.toString(), "Only Accept .gif,.jpeg,.png file");
    }

    @Override
    public CopyDamageReport setImageAndSaveCopyDamageReport(CopyDamageReport copyDamageReport, InputStream inputStream, MimeType mimeType) {
        try {
            copyDamageReport = this.setImage(copyDamageReport, inputStream, mimeType);
        } catch (IOException e) {
            throw new MongoGridFSException("inputStream hits IOException");
        }
        if (Objects.isNull(copyDamageReport)) {
            //image save fail
            throw new MongoGridFSException("CopyDamageReport's image fail to save");
        }
        this.mongoTemplate.save(copyDamageReport, "copyDamageReport");
        return copyDamageReport;

    }
}