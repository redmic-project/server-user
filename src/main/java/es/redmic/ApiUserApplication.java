package es.redmic;

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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import es.redmic.databaselib.common.repository.BaseRepositoryImpl;
import es.redmic.user.config.EntityManagerWrapper;
import es.redmic.user.config.OrikaScanBean;
import es.redmic.user.config.ResourceBundleMessageSource;
import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
@EnableWebSecurity
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "es.redmic.user" }, repositoryBaseClass = BaseRepositoryImpl.class)
public class ApiUserApplication {

	@Value("${info.microservice.name}")
	String microserviceName;

	public static void main(String[] args) {
		SpringApplication.run(ApiUserApplication.class, args);
	}

	@Bean
	public EntityManagerWrapper entityManagerWrapper() {
		return new EntityManagerWrapper();
	}

	@Bean
	public OrikaScanBean orikaScanBean() {
		return new OrikaScanBean();
	}

	@Bean
	public MessageSource messageSource() {

		return new ResourceBundleMessageSource();
	}

	@Bean
	MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
		return registry -> registry.config().commonTags("application", microserviceName);
	}
}
