package es.redmic.user.embedded.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

	@Value("${property.SUPERSET_PRIVATE_DASHBOARD_USERNAME}")
	private String supersetPrivateDashboardUsername;

	@Value("${property.SUPERSET_PRIVATE_DASHBOARD_PASSWORD}")
	private String supersetPrivateDashboardPassword;

	@Value("${property.SUPERSET_PUBLIC_DASHBOARD_USERNAME}")
	private String supersetPublicDashboardUsername;

	@Value("${property.SUPERSET_PUBLIC_DASHBOARD_PASSWORD}")
	private String supersetPublicDashboardPassword;

	@Autowired
	UserProfileService userProfileService;

	List<MediaType> acceptableMediaTypes = new ArrayList<>();

	HttpHeaders headers = new HttpHeaders();

	public SupersetEmbeddedService() {

		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
	}

	public String getToken(String dashboardid) {

		String username = userProfileService.getUsername();
		User profile = userProfileService.findProfileByUsername(username);

		Long roleId = profile.getRole().getId();

		//TODO: Cuando se realice la integración Superset + ECOMARCAN + OpenId, comprobar acceso del usuario al dashboard específico,
		// no de forma genérica como está ahora.
		try {
			if (roleId <= 2) {
				// Se trata de un usuario con permisos, por lo que se loguea contra superset con usuario embbeded
				return fetchGuestToken(supersetPrivateDashboardUsername, supersetPrivateDashboardPassword, dashboardid);
			} else if (roleId > 2 ) {
				// Se trata de un usuario sin permisos, por lo que se loguea contra superset con usuario guest
				return fetchGuestToken(supersetPublicDashboardUsername, supersetPublicDashboardPassword, dashboardid);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private String fetchGuestToken(String user, String password, String dashboardid) throws JSONException {
		String url = supersetApiUrl + supersetApiBasePath + "guest_token/";
		String accessToken = login(user, password);

		RestTemplate restTemplate = new RestTemplate();

		JSONObject body = new JSONObject(
			"{'resources': [{id': " + dashboardid + ", 'type': 'dashboard'}], 'rls': [], 'user': {'username': " + user + "}}");

		HttpHeaders authHeaders = headers;
		authHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		HttpEntity<JSONObject> request = new HttpEntity<>(body, authHeaders);

		JSONObject response = restTemplate.postForObject(url, request, JSONObject.class);

		if (response != null) {
			Object token = response.get("token");
			return (token != null) ? token.toString() : null;
		} else {
			return null;
		}
	}

	private String login(String user, String password) throws JSONException {

		String url = supersetApiUrl + supersetApiBasePath + "login";

		RestTemplate restTemplate = new RestTemplate();

		JSONObject body = new JSONObject(
			"{'username': " + user
			+ ", 'password': " + password
			+ ", 'provider': 'db', 'refresh': 'true'}");

		HttpEntity<JSONObject> request = new HttpEntity<>(body, headers);

		JSONObject response = restTemplate.postForObject(url, request, JSONObject.class);

		if (response != null) {
			Object accessToken = response.get("access_token");
			return (accessToken != null) ? accessToken.toString() : null;
		} else {
			return null;
		}
	}
}
