package com.devsuperior.dsmeta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    public static final String twilioSid = System.getenv("TWILIO_SID");
    public static final String twilioKey = System.getenv("TWILIO_KEY");
    public static final String twilioPhoneFrom = System.getenv("TWILIO_PHONE_FROM");
    public static final String twilioPhoneTo = System.getenv("TWILIO_PHONE_TO");
    
    @Autowired
    private SaleRepository saleRepository;
    
	public void sendSms(Long saleId) {
		
		Sale sale = saleRepository.findById(saleId).get();
		
		String date = sale.getDate().getMonthValue() + "/" + sale.getDate().getYear();
		
		String msg = "Vendedor " + sale.getSellerName() + " foi destaque em " + date 
				+ " com um total de R$ " + String.format("%.2f", sale.getAmount());

		Twilio.init(twilioSid, twilioKey);

		PhoneNumber to = new PhoneNumber(twilioPhoneTo);
		PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

		Message message = Message.creator(to, from, msg).create();

		System.out.println(message.getSid());
	}
}
