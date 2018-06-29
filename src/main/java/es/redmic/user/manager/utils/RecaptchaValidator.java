package es.redmic.user.manager.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RecaptchaValidatorImpl.class)
public @interface RecaptchaValidator {

	String message() default "Recaptcha not correct";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
