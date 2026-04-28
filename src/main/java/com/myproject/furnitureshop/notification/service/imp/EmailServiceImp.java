package com.myproject.furnitureshop.notification.service.imp;

import com.myproject.furnitureshop.notification.model.NotificationMessage;
import com.myproject.furnitureshop.notification.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImp implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImp(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendSimpleEmail(NotificationMessage message) throws MessagingException {
        Context context = new Context();

        context.setVariables(message.getTemplateVariables());

        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        String htmlContent = this.templateEngine.process(message.getTemplateName(), context);

        mimeMessageHelper.setTo(message.getRecipient());
        mimeMessageHelper.setSubject(message.getSubject());
        mimeMessageHelper.setText(htmlContent, true);

        this.mailSender.send(mimeMessage);
    }
}
