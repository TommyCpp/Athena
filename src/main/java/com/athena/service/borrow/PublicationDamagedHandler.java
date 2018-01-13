package com.athena.service.borrow;

import com.athena.model.Borrow;
import com.athena.model.SimpleCopy;
import com.athena.model.User;
import com.athena.model.domain.copy.CopyStatus;
import com.athena.model.domain.message.TemplateMessage;
import com.athena.repository.jpa.BorrowRepository;
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

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tommy on 2017/11/18.
 */
@Component
public class PublicationDamagedHandler {
    private static Logger logger = LoggerFactory.getLogger(PublicationDamagedHandler.class);
    private BorrowRepository borrowRepository;
    private TemplateBuilder templateBuilder;
    private String templateFile;
    private String title;
    private Map<String, User> systemUsers;
    private SystemMessageService systemMessageService;

    @Autowired
    public PublicationDamagedHandler(BorrowRepository borrowRepository, TemplateBuilder templateBuilder, @Value("${message.system.publicationDamageReport.template}") String templateFilePath, @Value("${message.system.publicationDamageReport.title}") String title, @Qualifier("systemUsers") Map<String, User> systemUsers, SystemMessageService systemMessageService) throws IOException {
        this.borrowRepository = borrowRepository;
        this.templateBuilder = templateBuilder;
        this.templateFile = Files.asCharSource(new ClassPathResource(templateFilePath).getFile(), Charsets.UTF_8).read();
        this.title = title;
        this.systemUsers = systemUsers;
        this.systemMessageService = systemMessageService;
    }

    public void handleDamage(User handler, SimpleCopy publicationCopy) {
        Borrow lastBorrow = this.borrowRepository.findFirstByCopyAndEnableIsFalseOrderByUpdatedDateDesc(publicationCopy);
        if (lastBorrow == null) {
            //if no borrow correspond to publicationCopy
            publicationCopy.setStatus(CopyStatus.AVAILABLE);
            logger.error("{}----{}---- Copy has not been borrowed", Calendar.getInstance().toString(), handler.toString());
        } else {
            Map<String, String> templateParas = new HashMap<>();
            templateParas.put("handlerName", handler.getUsername());
            templateParas.put("handlerId", handler.getId().toString());
            templateParas.put("lastKnownBorrowerName", lastBorrow.getUser().getUsername());
            templateParas.put("lastKnownBorrowerId", lastBorrow.getUser().getId().toString());
            TemplateMessage templateMessage = new TemplateMessage(this.title, templateParas, this.templateFile, templateBuilder, systemUsers.get("systemInfo"));
            this.systemMessageService.send(templateMessage);
        }
    }
}
