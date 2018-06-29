package es.redmic.user.common.deserializer;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import es.redmic.exception.databinding.DateTimeDeserializerException;

public class UserDateTimeDeserializer extends JsonDeserializer<DateTime> {

	@Override
	public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) {

		String dateTimeString = null;

		try {
			dateTimeString = jp.getText();
		} catch (IOException e) {
			throw new DateTimeDeserializerException("ISO DateTime", null, e);
		}

		return ISODateTimeFormat.dateTimeParser().parseDateTime(dateTimeString);

	}

}
