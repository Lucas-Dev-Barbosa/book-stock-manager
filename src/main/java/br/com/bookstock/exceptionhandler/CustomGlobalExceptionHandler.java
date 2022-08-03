package br.com.bookstock.exceptionhandler;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public String constraintViolationException(SQLIntegrityConstraintViolationException ex, RedirectAttributes attr, HttpServletRequest request) throws IOException, SQLIntegrityConstraintViolationException {
		String mensagemErro = "Este registro ja se encontra em nossa base";
		String uriOnError = request.getParameter("onerror");
		
		if(uriOnError == null || uriOnError.isEmpty())
			throw new SQLIntegrityConstraintViolationException(mensagemErro);
		
		attr.addFlashAttribute("error", mensagemErro);
		
		String uriRequest = request.getRequestURI();
		
		String[] arrUri = uriRequest
				.replace("/", " ")
				.trim()
				.split(" ");
		
		List<String> listUri = new ArrayList<>(Arrays.asList(arrUri));
		listUri.remove(listUri.size() - 1);
		
		listUri.add(uriOnError);
		
		String uriResponse = listUri.stream().reduce("", (uri1, uri2) -> uri1 + "/" + uri2);
		
		return "redirect:" + uriResponse;
	}

}
