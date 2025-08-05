package es.redmic.user.embedded.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.redmic.exception.security.NotAllowedException;
import es.redmic.user.manager.model.User;
import es.redmic.user.manager.service.UserProfileService;

/*-
 * #%L
 * User
 * %%
 * Copyright (C) 2025 REDMIC Project / Server
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

@Service
public class SupersetEmbeddedService {

	@Value("${property.SUPERSET_API_URL}")
	private String supersetApiUrl;

	@Value("${property.SUPERSET_API_BASE_PATH}")
	private String supersetApiBasePath;

	@Autowired
	UserProfileService userProfileService;

	List<MediaType> acceptableMediaTypes = new ArrayList<>();

	public SupersetEmbeddedService() {

		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
	}

	public Object getGuestToken(String dashboardid, String jwtToken) {

		String url = supersetApiUrl + supersetApiBasePath + "guest_token/";

		String username = userProfileService.getUsername();

		String csrfToken = getCSRFToken(jwtToken);

		RestTemplate restTemplate = new RestTemplate();

		String body = "{\"resources\": [{\"id\": \"" + dashboardid + "\", \"type\": \"dashboard\"}], \"rls\": [], \"user\": {\"username\": \"" + username + "\"}}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		headers.set("X-CSRFToken", csrfToken);
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
		HttpEntity<String> request = new HttpEntity<>(body, headers);

		return restTemplate.postForObject(url, request, String.class);
	}

	private String getCSRFToken(String jwtToken) {

		String url = supersetApiUrl + supersetApiBasePath + "csrf_token/";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
		HttpEntity<String> request = new HttpEntity<>(headers);

		String response = restTemplate.exchange(url, HttpMethod.GET, request, String.class).getBody();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response);
			return root.path("result").asText();
		} catch (IOException e) {
			throw new RuntimeException("Error parsing CSRF token response", e);
		}
	}
}
