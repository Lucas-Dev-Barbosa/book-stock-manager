package br.com.bookstock.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BigDecimalToStringConverter implements Converter<BigDecimal, String> {

	@Override
	public String convert(BigDecimal valor) {
		return valor.toString().replace(".", ",");
	}

}
