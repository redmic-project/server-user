package es.redmic;

import org.springframework.boot.SpringApplication;
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

@SpringBootApplication
@EnableWebSecurity
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "es.redmic.user" }, repositoryBaseClass = BaseRepositoryImpl.class)
public class ApiUserApplication {

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
}
