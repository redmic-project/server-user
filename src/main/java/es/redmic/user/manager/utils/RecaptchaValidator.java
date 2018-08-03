package es.redmic.user.manager.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import es.redmic.exception.user.RecaptchaNotValidException;
import es.redmic.utils.httpclient.HttpClient;

@Component
public class RecaptchaValidator {

	@Value("${recaptcha.secret}")
	String RECAPTCHA_SECRET;

	HttpClient client = new HttpClient();

	@SuppressWarnings("unchecked")
	public void checkRecaptcha(String reCaptcha) {

		if (reCaptcha == null) {
			throw new RecaptchaNotValidException();
		}

		String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + RECAPTCHA_SECRET + "&response="
				+ reCaptcha;

		Map<String, Object> result = (Map<String, Object>) client.get(url, Map.class);

		Boolean success = (Boolean) result.get("success");
		if (!success) {
			throw new RecaptchaNotValidException();
		}
	}
}
