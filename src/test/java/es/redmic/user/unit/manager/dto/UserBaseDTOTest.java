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

import es.redmic.user.manager.dto.UserBaseDTO;

public class UserBaseDTOTest extends DTOBaseTest<UserBaseDTO> {

	private static UserBaseDTO dto = new UserBaseDTO();

	@Before
	public void beforeTest() {

		dto.setFirstName("name");
		dto.setLastName("lastname");
		dto.setEmail("email@s.c");
	}

	@Test
	public void validationDTO_NoReturnError_IfDTOIsCorrect() {

		checkDTOHasNoError(dto);
	}

	@Test
	public void validationDTO_ReturnNotNullError_IfFirstNameIsNull() {

		dto.setFirstName(null);

		checkDTOHasError(dto, "firstname", "{javax.validation.constraints.NotNull.message}");
	}

	@Test
	public void validationDTO_ReturnSizeError_IfFirstNameExceedsSize() {

		dto.setFirstName("Nombre de más de 30 caracteres no está permitido");

		checkDTOHasError(dto, "firstname", "{javax.validation.constraints.Size.message}");
	}

	@Test
	public void validationDTO_ReturnNotNullError_IfLastNameIsNull() {

		dto.setLastName(null);

		checkDTOHasError(dto, "lastname", "{javax.validation.constraints.NotNull.message}");
	}

	@Test
	public void validationDTO_ReturnSizeError_IfLastNameExceedsSize() {

		dto.setLastName(
				"Apellidos de más de 90 caracteres no está permitido................................................................................................................................");

		checkDTOHasError(dto, "lastname", "{javax.validation.constraints.Size.message}");
	}

	@Test
	public void validationDTO_ReturnNotNullError_IfEmailIsNull() {

		dto.setEmail(null);

		checkDTOHasError(dto, "email", "{javax.validation.constraints.NotNull.message}");
	}

	@Test
	public void validationDTO_ReturnFormatError_IfEmailIsBadFormat() {

		dto.setEmail("email.c");

		checkDTOHasError(dto, "email", "{javax.validation.constraints.Email.message}");
	}

}
