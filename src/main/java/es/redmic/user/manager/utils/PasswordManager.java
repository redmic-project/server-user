package es.redmic.user.manager.utils;

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
