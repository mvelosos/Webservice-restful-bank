package com.api.componentes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Classe que transforma a data em milissegundos do banco de dados em uma data legível, como: "dd-MM-yyyy"
 * 
 * @author mateusveloso
 *
 */
public class JsonDateSerializer extends JsonSerializer<Date>{
	
	private static final SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
	
	/**
	 * Método que recebe uma Data como parâmetro e utiliza um formato de data predefinido para fazer a conversão.
	 * 
	 * @param date - Data em milissegundos que será convertida.
	 */
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) 
			throws IOException, JsonProcessingException {
		
	String dataFormatada = formato.format(date);
	
	gen.writeString(dataFormatada);
	}

}
