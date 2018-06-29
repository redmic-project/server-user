package es.redmic.user.common.serializer;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import es.redmic.exception.databinding.DateTimeSerializerException;

public class UserDateTimeSerializer extends JsonSerializer<DateTime> {

	@Override
	public void serialize(DateTime value, JsonGenerator gen, SerializerProvider arg2) {
		try {
			gen.writeString(ISODateTimeFormat.dateTime().print(value));
		} catch (IOException e) {
			throw new DateTimeSerializerException("ISO DateTime", value.toString(), e);
		}
	}
}
