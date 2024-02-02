package com.athena.service.communication;

import com.athena.model.communication.EmailDetails;

public interface EmailService {
	String sendSimpleMail(EmailDetails details);
}
