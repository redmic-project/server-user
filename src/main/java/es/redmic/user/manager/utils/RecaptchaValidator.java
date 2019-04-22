package es.redmic.user.manager.utils;

/*-
 * #%L
 * User
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
