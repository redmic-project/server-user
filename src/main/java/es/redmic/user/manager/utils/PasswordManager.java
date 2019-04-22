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

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordManager {
	
	private static String idForEncode = "bcrypt";
	
	private static Map<String, PasswordEncoder> encoders = new HashMap<>();
	
	private static DelegatingPasswordEncoder encoder;
	
	private static void build() {
		
		encoders.put(idForEncode, new BCryptPasswordEncoder(10));

		encoder = new DelegatingPasswordEncoder(idForEncode, encoders);
		encoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder(10));
	}

	public static String encrypt(String password) {
		if (encoder == null)
			build();
		return encoder.encode(password);
	}

	public static boolean match(String rawPassword, String encodedPassword) {
		if (encoder == null)
			build();
		return encoder.matches(rawPassword, encodedPassword);
	}
}
