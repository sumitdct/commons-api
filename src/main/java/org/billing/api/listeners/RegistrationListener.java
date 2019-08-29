package org.billing.api.listeners;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.billing.api.entities.UserEntity;
import org.billing.api.service.CommonsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteListener> {

    @Autowired
    private CommonsApiService commonsApiService;


   /* @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration configuration;*/


    @Override
    public void onApplicationEvent(OnRegistrationCompleteListener event) {
        this.confirmRegistration(event);
    }
    private void confirmRegistration(OnRegistrationCompleteListener event) {
        /*UserEntity userEntity= event.getUserEntity();
        String token = UUID.randomUUID().toString();
        commonsApiService.createVerificationToken(userEntity, token);
        configuration .setClassForTemplateLoading(this.getClass(),"/templates");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", userEntity.getName());
        model.put("verifyUrl", "http://localhost:2001/verify/"+token);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        Template t = null;
        try {
            t = configuration.getTemplate("email-verification.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(t,model);

            helper.setTo(userEntity.getEmail());
            helper.setFrom("no-reply@apirus.com");
            helper.setSubject("Apirus - Verify email account");
            helper.setText(text,true);
            mailSender.send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (TemplateException e) {
            e.printStackTrace();
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }*/
    }
}

