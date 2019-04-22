package es.redmic.user.unit.common.utils;

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

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import es.redmic.exception.user.RecaptchaNotValidException;
import es.redmic.user.manager.utils.RecaptchaValidator;

public class RecaptchaValidatorTest {

	private static RecaptchaValidator recaptchaValidator = new RecaptchaValidator();

	@BeforeClass
	public static void setUp() {
		Whitebox.setInternalState(recaptchaValidator, "RECAPTCHA_SECRET", "ffff");
	}

	// No se puede testear el correcto funcionamiento porque el recaptcha correcto
	// solo se puede obtener desde una interfaz de usuario.

	@Test(expected = RecaptchaNotValidException.class)
	public void recaptchaValidator_ThrowException_IfRecaptchaIsNull() {

		recaptchaValidator.checkRecaptcha(null);
	}

	@Test(expected = RecaptchaNotValidException.class)
	public void recaptchaValidator_ThrowException_IfRecaptchaIsNotValid() {

		recaptchaValidator.checkRecaptcha("prueba");
	}
}
