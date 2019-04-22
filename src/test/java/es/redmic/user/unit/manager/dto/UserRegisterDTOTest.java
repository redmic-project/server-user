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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import es.redmic.user.manager.dto.UserRegisterDTO;

@RunWith(MockitoJUnitRunner.class)
public class UserRegisterDTOTest extends DTOBaseTest<UserRegisterDTO> {

	private static UserRegisterDTO dto = new UserRegisterDTO();

	@Before
	public void beforeTest() {
		// dto base
		dto.setFirstName("name");
		dto.setId(1L);
		dto.setLastName("lastname");
		dto.setEmail("email@s.c");
		dto.setReCaptcha("recaptcha");

		dto.setAccept(true);
		dto.setPassword("password");
	}

	@Test
	public void validationDTO_NoReturnError_IfDTOIsCorrect() {

		checkDTOHasNoError(dto);
	}

	@Test
	public void validationDTO_ReturnSizeError_IfPasswordHasInsufficientLength() {

		dto.setPassword("ii");

		checkDTOHasError(dto, "password", "{javax.validation.constraints.Size.message}");
	}

	@Test
	public void validationDTO_ReturnNotNullError_IfPasswordIsNull() {

		dto.setPassword(null);

		checkDTOHasError(dto, "password", "{javax.validation.constraints.NotNull.message}");
	}

	@Test
	public void validationDTO_ReturnAssertTrueError_IfValueIsFalse() {

		dto.setAccept(false);

		checkDTOHasError(dto, "accept", "{javax.validation.constraints.AssertTrue.message}");
	}

	@Test
	public void validationDTO_ReturnNotNullError_IfReCaptchaIsNull() {

		dto.setReCaptcha(null);

		checkDTOHasError(dto, "reCaptcha", "{javax.validation.constraints.NotNull.message}");
	}
}
