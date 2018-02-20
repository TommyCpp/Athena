package com.athena.service.borrow;

import com.athena.model.borrow.Borrow;
import com.athena.model.copy.CopyDamageReport;
import com.athena.model.copy.CopyStatus;
import com.athena.model.copy.SimpleCopy;
import com.athena.model.message.TemplateMessage;
import com.athena.model.security.User;
import com.athena.repository.jpa.BorrowRepository;
import com.athena.repository.mongo.CopyDamageReportRepository;
import com.athena.service.message.SystemMessageService;
import com.athena.util.TemplateBuilder;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Tommy on 2017/11/18.
 */
@Component
public class PublicationDamagedHandler {
    private static Logger logger = LoggerFactory.getLogger(PublicationDamagedHandler.class);
    private CopyDamageReportRepository copyDamageReportRepository;
    private BorrowRepository borrowRepository;
    private TemplateBuilder templateBuilder;
    private String templateFile;
    private String title;
    private Map<String, User> systemUsers;
    private SystemMessageService systemMessageService;

    /**
     * Instantiates a new Publication damaged handler.
     *
     * @param borrowRepository     the borrow repository
     * @param templateBuilder      the template builder
     * @param templateFilePath     the template file path
     * @param title                the title
     * @param systemUsers          the system users
     * @param systemMessageService the system message service
     * @throws IOException the io exception
     */
    @Autowired
    public PublicationDamagedHandler(BorrowRepository borrowRepository, TemplateBuilder templateBuilder, @Value("${message.system.publicationDamageReport.template}") String templateFilePath, @Value("${message.system.publicationDamageReport.title}") String title, @Qualifier("systemUsers") Map<String, User> systemUsers, SystemMessageService systemMessageService, CopyDamageReportRepository copyDamageReportRepository) throws IOException {
        this.borrowRepository = borrowRepository;
        this.templateBuilder = templateBuilder;
        this.templateFile = Files.asCharSource(new ClassPathResource(templateFilePath).getFile(), Charsets.UTF_8).read();
        this.title = title;
        this.systemUsers = systemUsers;
        this.systemMessageService = systemMessageService;
        this.copyDamageReportRepository = copyDamageReportRepository;
    }

    /**
     * handle damaged copy.
     * <p>
     * First confirming the copy has borrowed by someone (copy has lastBorrow).
     * <p>
     * Second adding correspond CopyDamageReport.
     * <p>
     * Last sending notification.
     *
     * @param handler         the handler
     * @param publicationCopy the publication copy
     * @param description
     */
    public void handleDamage(User handler, SimpleCopy publicationCopy, String description) {
        Borrow lastBorrow = this.borrowRepository.findFirstByCopyAndEnableIsFalseOrderByUpdatedDateDesc(publicationCopy);
        if (lastBorrow == null) {
            //if no borrow correspond to publicationCopy
            publicationCopy.setStatus(CopyStatus.AVAILABLE);
            logger.error("{}----{}---- Copy has not been borrowed", Calendar.getInstance().toString(), handler.toString());
        } else {
            //todo: divide add CopyDamageReport and send message

            // Send notification to admins
            Map<String, String> templateParas = new HashMap<>();
            templateParas.put("handlerName", handler.getUsername());
            templateParas.put("handlerId", handler.getId().toString());
            templateParas.put("lastKnownBorrowerName", lastBorrow.getUser().getUsername());
            templateParas.put("lastKnownBorrowerId", lastBorrow.getUser().getId().toString());
            TemplateMessage templateMessage = new TemplateMessage(this.title, templateParas, this.templateFile, templateBuilder, systemUsers.get("systemInfo"));
            this.systemMessageService.send(templateMessage);
        }
    }

    public CopyDamageReport createCopyDamageReport(Long handlerId, Long copyId, Borrow lastKnownBorrow, String description) {
        CopyDamageReport copyDamageReport = new CopyDamageReport();
        copyDamageReport.setId(UUID.randomUUID());
        copyDamageReport.setHandlerId(handlerId);
        copyDamageReport.setCopyId(copyId);
        copyDamageReport.setLastKnownBorrow(lastKnownBorrow);
        copyDamageReport.setDescription(description);
        return this.copyDamageReportRepository.save(copyDamageReport);
    }

    public CopyDamageReport createCopyDamageReport(Long handlerId, Long copyId, Borrow lastKnownBorrow, String description, MultipartFile image) throws IOException {
        //todo:test
        CopyDamageReport copyDamageReport = this.createCopyDamageReport(handlerId, copyId, lastKnownBorrow, description);
        return this.copyDamageReportRepository.setImageAndSaveCopyDamageReport(copyDamageReport, image.getInputStream(), MimeTypeUtils.parseMimeType(image.getContentType()));
    }
}
