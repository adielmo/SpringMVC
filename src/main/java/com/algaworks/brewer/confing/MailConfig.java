package com.algaworks.brewer.confing;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.algaworks.brewer.mail.Mailer;

@Configuration
@ComponentScan(basePackageClasses=Mailer.class)
@PropertySource({"classpath:env/mail-${ambiente:local}.properties"})
@PropertySource(value={ "file:\\${USERPROFILE}\\.brewer-mail.properties" }, ignoreResourceNotFound=true)
public class MailConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public JavaMailSender mailSender() {		
		
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		  mailSenderImpl.setHost("smtp.sendgrid.net");
		  mailSenderImpl.setPort(587);
		  mailSenderImpl.setUsername(env.getProperty("adielmorabelo@gmail.com"));
		  mailSenderImpl.setPassword(env.getProperty("password"));
		/*  
		  System.out.println("username: " + env.getProperty("adielmorabelo@gmail.com"));
		  System.out.println("password: " + env.getProperty("password"));*/
		  
		  Properties properties = new Properties();
		  properties.put("mail.transport.protocol", "smtp");
		  properties.put("mail.smtp.auth", true);
		  properties.put("mail.smtp.starttls.enable", true);
		  properties.put("mail.debug", false);
		  properties.put("mail.smtp.connectiontimeout", 10000); // miliseconds
		  
		  mailSenderImpl.setJavaMailProperties(properties);
		
		
		return mailSenderImpl;
	}

}
