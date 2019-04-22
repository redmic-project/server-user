package es.redmic.user.unit.manager.dto;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;

public class DTOBaseTest<TDTO extends Object> {
	
	private Map<String, String> ignoreErrors = new HashMap<String, String>();

	protected static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	protected void checkDTOHasNoError(TDTO dto) {
		
		ConstraintViolation<TDTO> constraintViolation = getConstraintViolation(validator.validate(dto));
		
		assertNull(constraintViolation);
	}
	
	protected void checkDTOHasError(TDTO dto, String property, String messageTemplate) {
		
		ConstraintViolation<TDTO> constraintViolation = getConstraintViolation(validator.validate(dto));
		
		assertNotNull(constraintViolation);
		assertEquals(property, constraintViolation.getPropertyPath().toString());
		assertEquals(messageTemplate, constraintViolation.getMessageTemplate());
	}
	
	protected ConstraintViolation<TDTO> getConstraintViolation(Set<ConstraintViolation<TDTO>> set) {
		
		for (ConstraintViolation<TDTO> violation: set) {
			
			if (!(ignoreErrors.containsKey(violation.getPropertyPath().toString()) && ignoreErrors.containsValue(violation.getMessageTemplate().toString()))) {
				return violation;
			}
		}
		return null;
	}

	public Map<String, String> getIgnoreErrors() {
		return ignoreErrors;
	}

	public void setIgnoreErrors(Map<String, String> ignoreErrors) {
		this.ignoreErrors = ignoreErrors;
	}
	
	@SuppressWarnings("serial")
	public void addIgnoreError(String error, String message) {
		this.ignoreErrors = new HashMap<String, String>() {{ put(error, message); }};
	}
}
