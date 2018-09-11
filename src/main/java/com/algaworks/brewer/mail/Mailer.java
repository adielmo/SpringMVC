package com.algaworks.brewer.mail;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import antlr.debug.TraceAdapter;

@Component
public class Mailer {
	
	@Async
	public void enviar() {
		
		System.out.println("enviando e-mail.....");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("E-mail enviado "  );
	}

}
