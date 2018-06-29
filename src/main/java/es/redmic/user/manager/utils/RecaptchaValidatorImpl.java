package es.redmic.user.manager.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.redmic.exception.common.ExceptionType;
import es.redmic.exception.common.InternalException;
import es.redmic.exception.utils.ExternalResourceException;

public class RecaptchaValidatorImpl implements ConstraintValidator<RecaptchaValidator, String> {

	@Override
	public void initialize(RecaptchaValidator arg0) {
	}

	@Override
	public boolean isValid(String recaptchaValue, ConstraintValidatorContext ctx) {

		if (recaptchaValue == null)
			return false;

		Boolean isValidRecaptcha = false;
		isValidRecaptcha = getReCaptchaResponse(recaptchaValue, ctx);
		return isValidRecaptcha;
	}

	public Boolean getReCaptchaResponse(String reCaptcha, ConstraintValidatorContext ctx) {

		String secret = "secret";
		String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + secret + "&response=" + reCaptcha;

		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		HttpResponse response;
		try {
			response = client.execute(request);
		} catch (IOException e) {
			throw new ExternalResourceException(e, url);
		}

		BufferedReader rd = null;
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (UnsupportedOperationException | IOException e) {
			throw new InternalException(ExceptionType.INTERNAL_EXCEPTION, e);
		}

		StringBuffer result = new StringBuffer();
		String line = "";

		try {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e1) {
			throw new InternalException(ExceptionType.INTERNAL_EXCEPTION, e1);
		}

		ObjectMapper mapper = new ObjectMapper();
		JsonNode resultJSON = null;
		try {
			resultJSON = mapper.readTree(result.toString());
		} catch (IOException e) {
			throw new InternalException(ExceptionType.INTERNAL_EXCEPTION, e);
		}

		if (resultJSON.get("success").asBoolean())
			return true;
		else {
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate("Recaptcha not correct").addConstraintViolation();
			return false;
		}
	}
}
