package nz.gershunov.testtask.pccw.email;

import nz.gershunov.testtask.pccw.model.User;

import org.springframework.core.env.Environment;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class SendEmailSMTP
{
    public String sendEmail(Environment environment, User user)
    {
        Properties prop = System.getProperties();
        String smtpServer = environment.getProperty("app.email.smtp.server");
        prop.put("mail.smtp.host", smtpServer);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", environment.getProperty("app.email.smtp.port"));

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {
            msg.setFrom(new InternetAddress(environment.getProperty("app.email.from")));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail(), false));

            msg.setSubject("Registration confirmation");

            String content = "  Hello " + user.getFirstName() + " " + user.getLastName() + ", \n\n" +
                             "congratulations for the registration on our global portal.\n";
            msg.setText(content);

            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport)session.getTransport("smtp");

            String username = environment.getProperty("app.email.smtp.username");
            String password = environment.getProperty("app.email.smtp.password");
            t.connect(smtpServer, username, password);

            t.sendMessage(msg, msg.getAllRecipients());
            t.close();

            return content;
        } catch (MessagingException e) {
        }
        return "";
    }

}
