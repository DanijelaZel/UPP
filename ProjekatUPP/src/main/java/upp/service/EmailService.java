package upp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import upp.model.User;

@Service
public class EmailService {

	
	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
	 */
	
	private SimpleMailMessage mail = new SimpleMailMessage();
	
	public void sendNotificaitionSync(User user) throws MailException, InterruptedException {

		//Simulacija duze aktivnosti da bi se uocila razlika
		Thread.sleep(100);
		
		
		System.out.println(mail.getText());
		javaMailSender.send(mail);
		}

	public SimpleMailMessage getMail() {
		return mail;
	}

	public void setMail(SimpleMailMessage mail) {
		this.mail = mail;
	}
	
	
	
}
