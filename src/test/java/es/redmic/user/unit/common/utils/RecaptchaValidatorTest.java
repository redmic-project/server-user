package es.redmic.user.unit.common.utils;

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
