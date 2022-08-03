package br.com.bookstock.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String valor) {
		valor = valor
				.replace(".", "")
				.replace(",", ".");
		
		return new BigDecimal(valor);
	}

}
