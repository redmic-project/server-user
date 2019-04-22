package es.redmic.user.common.serializer;

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
