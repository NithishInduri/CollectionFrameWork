package com.insurence.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {
	private Logger logger = LoggerFactory.getLogger(EmailUtils.class);
	@Autowired
	private JavaMailSender mailSender;

	public boolean sendMail(String to, String subject, String body) {

		boolean isMailSent = false;
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			mailSender.send(message);
			isMailSent = true;
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return isMailSent;
	}

}
