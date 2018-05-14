package com.api.bancaria.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.bancaria.model.Conta;
import com.api.bancaria.repository.ContaRepo;

@Service
public class LimiteDiarioService {
	
	@Autowired
	private ContaRepo contaRepo;
	
	public void resetarLimiteDiario() {
			
			Calendar c = Calendar.getInstance();
		    c.set(Calendar.HOUR_OF_DAY, 20);
		    c.set(Calendar.MINUTE, 01);
		    c.set(Calendar.SECOND, 50);
		    
		    Date time = c.getTime();
			
	        Timer t = new Timer();
	        List<Conta> contas = contaRepo.findAll();
	        t.schedule(new TimerTask() {
				
				@Override
				public void run() {
					System.out.println("rodou lindo!");
					System.out.println(c.getTime().toString());
					System.out.println(contaRepo.findOne(new Long(1)));
					/*List<Conta> contas = contaRepo.findAll();
					System.out.println(contas);
					for (Conta conta : contas) {
						conta.setLimiteSaqueDiario(new BigDecimal(1500));
						contaRepo.save(conta);
					}*/
				}
			}, time, 60000);
	    }

}
