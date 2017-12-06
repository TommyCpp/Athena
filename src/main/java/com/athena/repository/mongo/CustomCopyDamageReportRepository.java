package com.athena.repository.mongo;

import com.athena.model.CopyDamageReport;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.util.MimeType;

import java.io.InputStream;

/**
 * Created by Tommy on 2017/11/30.
 */
public interface CustomCopyDamageReportRepository {

    /**
     * Gets image.
     *
     * @param copyDamageReport the copy damage report
     * @return the image
     */
    GridFSDBFile getImage(CopyDamageReport copyDamageReport);

    /**
     * Sets image.
     * <p>
     * Note that this function does not store the copyDamageReport with ImageId.
     *
     * @param copyDamageReport the copy damage report
     * @param inputStream      the input stream
     * @param mimeType         the mime type
     * @return the image
     * @see #setImageAndSaveCopyDamageReport(CopyDamageReport, InputStream, MimeType)
     */
    CopyDamageReport setImage(CopyDamageReport copyDamageReport, InputStream inputStream, MimeType mimeType);


    /**
     * Sets image and save copyDamageReport.
     *
     * @param copyDamageReport the copy damage report
     * @param inputStream      the input stream
     * @param mimeType         the mime type
     * @return the image and save copy damage report
     */
    CopyDamageReport setImageAndSaveCopyDamageReport(CopyDamageReport copyDamageReport, InputStream inputStream, MimeType mimeType);
}
