package es.redmic.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
public class Oauth2SecurityConfiguration {

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		private static final String SPARKLR_RESOURCE_ID = "sparklr";

		@Value("${oauth.check_token.endpoint}")
		String checkTokenEndpoint;
		@Value("${oauth.client.id}")
		String clientId;
		@Value("${oauth.client.secret}")
		String secret;

		@Primary
		@Bean
		public RemoteTokenServices tokenService() {
			RemoteTokenServices tokenService = new RemoteTokenServices();
			tokenService.setCheckTokenEndpointUrl(checkTokenEndpoint);
			tokenService.setClientId(clientId);
			tokenService.setClientSecret(secret);
			return tokenService;
		}

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.tokenServices(tokenService())
					.resourceId(SPARKLR_RESOURCE_ID);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off

			http.anonymous().and().authorizeRequests().antMatchers("/user/actuator/**").permitAll()
					.antMatchers(HttpMethod.GET, "/user/profile/").permitAll()
					.antMatchers(HttpMethod.GET, "/user/modules/openmodules/").permitAll()
					.antMatchers(HttpMethod.POST, "/user/register/**/").permitAll()
					.antMatchers(HttpMethod.POST, "/user/feedback/").permitAll()
					.antMatchers(HttpMethod.POST, "/user/profile/resettingRequest/**/").permitAll()
					.antMatchers(HttpMethod.POST, "/user/profile/resettingSetPassword/**/").permitAll()
					.antMatchers(HttpMethod.POST, "/user/activateAccount/**/").permitAll();

			http.authorizeRequests().antMatchers("/**/").access(
					"#oauth2.hasScope('read') or #oauth2.hasScope('write') and hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_OAG', 'ROLE_COLLABORATOR', 'ROLE_USER')");
		}
	}

}
