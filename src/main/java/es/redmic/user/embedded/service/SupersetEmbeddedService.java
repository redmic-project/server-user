package es.redmic.user.embedded.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

	@Value("${SUPERSET_PRIVATE_DASHBOARD_USERNAME}")
	private String supersetPrivateDashboardUsername;

	@Value("${SUPERSET_PRIVATE_DASHBOARD_PASSWORD}")
	private String supersetPrivateDashboardPassword;

	@Value("${SUPERSET_PUBLIC_DASHBOARD_USERNAME}")
	private String supersetPublicDashboardUsername;

	@Value("${SUPERSET_PUBLIC_DASHBOARD_PASSWORD}")
	private String supersetPublicDashboardPassword;

	@Autowired
	UserProfileService userProfileService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	List<MediaType> acceptableMediaTypes = new ArrayList<>();

	HttpHeaders headers = new HttpHeaders();

	public SupersetEmbeddedService() {

		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
	}

	public Object getToken(String dashboardid) {

		String username = userProfileService.getUsername();
		User profile = userProfileService.findProfileByUsername(username);

		Long roleId = profile.getRole().getId();

		//TODO: Cuando se realice la integración Superset + ECOMARCAN + OpenId, comprobar acceso del usuario al dashboard específico,
		// no de forma genérica como está ahora.

		if (roleId <= 2) {
			// Se trata de un usuario con permisos, por lo que se loguea contra superset con usuario embbeded
			return fetchGuestToken(supersetPrivateDashboardUsername, supersetPrivateDashboardPassword, dashboardid);
		} else if (roleId > 2 ) {
			// Se trata de un usuario sin permisos, por lo que se loguea contra superset con usuario guest
			return fetchGuestToken(supersetPublicDashboardUsername, supersetPublicDashboardPassword, dashboardid);
		}
		throw new NotAllowedException();
	}

	private Object fetchGuestToken(String user, String password, String dashboardid) {

		String url = supersetApiUrl + supersetApiBasePath + "guest_token/";

		String accessToken;

		try {
			accessToken = login(user, password);
		} catch (IOException e) {
			throw new NotAllowedException();
		}


		RestTemplate restTemplate = new RestTemplate();

		String body = "{'resources': [{'id': " + dashboardid + ", 'type': 'dashboard'}], 'rls': [], 'user': {'username': '" + user + "'}}";

		HttpHeaders authHeaders = headers;
		authHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		HttpEntity<String> request = new HttpEntity<>(body, authHeaders);

		System.out.println("Hacieniendo petición de token en URL " + url + " con body " + body);

		return restTemplate.postForObject(url, request, String.class);
	}

	private String login(String user, String password) throws IOException {

		String url = supersetApiUrl + supersetApiBasePath + "login";

		RestTemplate restTemplate = new RestTemplate();

		String body = "{'username': '" + user
			+ "', 'password': '" + password
			+ "', 'provider': 'db', 'refresh': 'true'}";

		HttpEntity<String> request = new HttpEntity<>(body, headers);

		System.out.println("Hacieniendo petición de login en URL " + url + " con body " + "{'username': '" + user
			+ "', 'password': 'xxx', 'provider': 'db', 'refresh': 'true'}");

		String response = restTemplate.postForObject(url, request, String.class);

		if (response != null) {

			JsonNode root = objectMapper.readTree(response);
			String accessToken = root.path("access_token").asText();
			return (accessToken != null) ? accessToken : null;
		} else {
			throw new NotAllowedException();
		}
	}
}
