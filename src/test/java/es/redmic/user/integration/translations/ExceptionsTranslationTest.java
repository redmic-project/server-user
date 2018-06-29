package es.redmic.user.integration.translations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.text.MessageFormat;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.redmic.ApiUserApplication;
import es.redmic.exception.common.PatternUtils;
import es.redmic.exception.database.DBNotFoundException;
import es.redmic.user.integration.ApiUserApplicationTest;
import es.redmic.user.manager.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = { ApiUserApplication.class, ApiUserApplicationTest.class })
@ActiveProfiles(profiles = { "test" })
public class ExceptionsTranslationTest {

	@Autowired
	MessageSource messageSource;

	@Mock
	UserRepository repository;

	protected static final String resourcePathSpanish = "classpath*:i18n/messages_es_ES.properties",
			resourcePathEnglish = "classpath*:i18n/messages_en_EN.properties";

	@Before
	public void init() {

		when(repository.findByEmail(any())).thenThrow(new DBNotFoundException("email", "prueba@redmic.es"));
	}

	@Test
	public void getEnglishMessage_returnI18nMessageInDefaultLocale_WhenCodePropertyExist() {

		try {
			repository.findByEmail("prueba@redmic.es");
		} catch (DBNotFoundException e) {

			String code = e.getCode().toString();

			String[] fields = (String[]) e.getFieldErrors().toArray();

			String mess = messageSource.getMessage(code, fields, new Locale("en", "EN"));

			Assert.assertEquals(getMessage(code, fields, resourcePathEnglish), mess);
		}
	}

	@Test
	public void getSpanishMessage_returnI18nMessage_WhenCodePropertyExist() {

		try {
			repository.findByEmail("prueba@redmic.es");
		} catch (DBNotFoundException e) {

			String code = e.getCode().toString();

			String[] fields = (String[]) e.getFieldErrors().toArray();

			String mess = messageSource.getMessage(code, fields, new Locale("es", "ES"));

			Assert.assertEquals(getMessage(code, fields, resourcePathSpanish), mess);
		}
	}

	@Test
	public void getRussianLanguageMessage_returnDefaultI18nMessage_WhenI18nFileNotExist() {

		try {
			repository.findByEmail("prueba@redmic.es");
		} catch (DBNotFoundException e) {

			String code = e.getCode().toString();

			String[] fields = (String[]) e.getFieldErrors().toArray();

			String mess = messageSource.getMessage(code, fields, new Locale("ru", "RU"));

			Assert.assertEquals(getMessage(code, fields, resourcePathSpanish), mess);
		}
	}

	@Test
	public void getMessage_returnCode_WhenCodeNotInI18nFile() {

		String code = "CodeNotFound";

		String mess = messageSource.getMessage(code, null, new Locale("es", "ES"));

		Assert.assertEquals(code, mess);
	}

	private String getMessage(String code, String[] fields, String resourcePath) {

		String message = PatternUtils.getPattern(code, resourcePath);
		MessageFormat format = new MessageFormat(message);

		return format.format(fields);
	}

}
