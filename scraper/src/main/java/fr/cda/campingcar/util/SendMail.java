package fr.cda.campingcar.util;


import fr.cda.campingcar.Controller.MailController;
import fr.cda.campingcar.util.*;
import javafx.application.Platform;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Classe de gestion de l'envoi du courriel
 */
public class SendMail implements Runnable {

   /**
    * String recipient : email utilisateur
    */
   private
   final String recipient;

   private String body;
   private String subject;
   private List<File> attachments ;

   private final MailController controller;
   public SendMail( MailController controller ,String recipient, String subject, String body) {

      this.recipient = recipient;
      this.subject = subject;
      this.body = body;
      this.controller = controller;
   }

   public List<File> getAttachments() {
      return attachments;
   }

   public void setAttachments(List<File> attachments) {
      this.attachments = attachments;
   }

   /**
    * Méthode d'envoi du courriel
    *
    * @return reponse de l'API brevo suite à l'envoi
    * @throws Exception échec de l'envoi
    */
   public CreateSmtpEmail checkMail() throws Exception {
      try {
      ApiClient defaultClient = Configuration.getDefaultApiClient();
      // Configure API key authorization: api-key
      ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
      apiKey.setApiKey(Setting.API_KEY);

      TransactionalEmailsApi api = new TransactionalEmailsApi();

      //Prepare sender
      SendSmtpEmailSender sender = new SendSmtpEmailSender();
      sender.setEmail(Setting.FROM_MAIL);
      sender.setName(Setting.FROM_MAIL_NAME);

         // Prepare recipient
      List<SendSmtpEmailTo> toList = new ArrayList<>();
      SendSmtpEmailTo to = new SendSmtpEmailTo();
      to.setEmail(recipient);
      to.setName(Setting.TO_EMAIL_NAME);
      toList.add(to);

         // Prepare CC
//      List<SendSmtpEmailCc> ccList = new ArrayList<>();
//      SendSmtpEmailCc cc = new SendSmtpEmailCc();
//      cc.setEmail(Setting.TO_EMAIL_CC);
//      cc.setName(Setting.TO_EMAIL_NAME_CC);
//      ccList.add(cc);
//         // Prepare BCC
//      List<SendSmtpEmailBcc> bccList = new ArrayList<>();
//      SendSmtpEmailBcc bcc = new SendSmtpEmailBcc();
//      bcc.setEmail(Setting.TO_EMAIL_BCC);
//      bcc.setName(Setting.TO_EMAIL_NAME_BCC);
//      bccList.add(bcc);
//         // Prepare reply-to
//      SendSmtpEmailReplyTo replyTo = new SendSmtpEmailReplyTo();
//      replyTo.setEmail(Setting.REPLY_EMAIL);
//      replyTo.setName(Setting.REPLY_EMAIL_NAME);

         // Prepare email

      //  Properties headers = new Properties();
      //headers.setProperty("Some-Custom-Name", "unique-id-1234");
      // Properties params = new Properties();
      //params.setProperty("parameter", "My param value");
      // params.setProperty("subject", Constantes.SUBJECT_EMAIL);
      SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
      sendSmtpEmail.setSender(sender);
      sendSmtpEmail.setTo(toList);
//      sendSmtpEmail.setCc(ccList);
//      sendSmtpEmail.setBcc(bccList);
      sendSmtpEmail.setHtmlContent(this.body);
      sendSmtpEmail.setSubject(this.subject);
//      sendSmtpEmail.setReplyTo(replyTo);
//      sendSmtpEmail.setAttachment(attachmentList);
      // sendSmtpEmail.setHeaders(headers);
      //sendSmtpEmail.setParams(params);

         // Prepare attachment
         if (attachments != null && !attachments.isEmpty()) {

            List<SendSmtpEmailAttachment> attachmentList = new ArrayList<>();

            for (File file : attachments) {
               if (file.exists()){

                  SendSmtpEmailAttachment emailAttachment = new SendSmtpEmailAttachment();
                  emailAttachment.setName(file.getName());

                  Path path = file.toPath();
                  byte[] fileContent = Files.readAllBytes(path);
                  emailAttachment.setContent(fileContent);

                  attachmentList.add(emailAttachment);

               }
            }

            sendSmtpEmail.setAttachment(attachmentList);

         }

         // Send the email
         // retour de la réponse de l'envoi
         return api.sendTransacEmail(sendSmtpEmail);
      } catch (Exception e) {
         throw new Exception("Erreur d'envoi du courriel");
      }
   }

   @Override
   public void run() {
      try {
         this.checkMail();

         Platform.runLater(() -> {
            controller.getSuccessLabel().setText("Mail sent successfully!");

         });

      } catch (Exception e) {
         Platform.runLater(() -> {
            controller.getErrorLabel().setText("Error: " + e.getMessage());         });
      }
   }

}
