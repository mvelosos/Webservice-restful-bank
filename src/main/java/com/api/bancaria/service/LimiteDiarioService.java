package com.api.bancaria.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.api.bancaria.model.Conta;
import com.api.bancaria.repository.ContaRepo;

/**
 * Essa classe contém um listener que é iniciado assim que a aplicação iniciar. O objetivo da classe é verificar a hora do dia
 * para que o limiteSaqueDiario das contas possa ser resetado, sendos assim possível realizar um saque no dia seguinte.
 * O limiteSaqueDiario é resetado as 23:59:59 todos os dias.
 * 
 * @author mateusveloso
 *
 */
@Service
public class LimiteDiarioService {
	
	@Autowired
	private ContaRepo contaRepo;
	
	/**
	 * Método que contém a anotação para iniciar o listener assim que a aplicação for iniciada.
	 * 
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void resetarLimiteDiario() {
			
			Calendar c = Calendar.getInstance();
		    c.set(Calendar.HOUR_OF_DAY, 23);
		    c.set(Calendar.MINUTE, 59);
		    c.set(Calendar.SECOND, 59);
		    
		    Date time = c.getTime();
			
	        Timer t = new Timer();
	        t.schedule(new TimerTask() {
				
	        	/**
	        	 * Método que realiza uma operação em uma determinada data e em um determinado intervalo de tempo.
	        	 * 86400000 milissegundos == 24 horas.
	        	 * 
	        	 */
				@Override
				public void run() {
					System.out.println(c.getTime().toString());
					List<Conta> contas = contaRepo.findAll();
					System.out.println(contas);
					for (Conta conta : contas) {
						conta.setLimiteSaqueDiario(new BigDecimal(1500));
						contaRepo.save(conta);
					}
				}
			}, time, 86400000);
	    }

}
