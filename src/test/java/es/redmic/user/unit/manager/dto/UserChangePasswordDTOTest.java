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

import es.redmic.user.manager.dto.UserChangePasswordDTO;

public class UserChangePasswordDTOTest extends DTOBaseTest<UserChangePasswordDTO> {

	private static UserChangePasswordDTO dto = new UserChangePasswordDTO();

	@Before
	public void beforeTest() {
		
		dto.setPassword("password");
		dto.setOldPassword("oldPassword");
	}
	
	@Test
	public void validationDTO_NoReturnError_IfDTOIsCorrect() {

		dto.setPassword("password");
		dto.setOldPassword("oldPassword");

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
	public void validationDTO_ReturnSizeError_IfOldPasswordHasInsufficientLength() {

		dto.setOldPassword("ii");
		
		checkDTOHasError(dto, "oldPassword", "{javax.validation.constraints.Size.message}");
	}

	@Test
	public void validationDTO_ReturnNotNullError_IfOldPasswordIsNull() {

		dto.setOldPassword(null);
		
		checkDTOHasError(dto, "oldPassword", "{javax.validation.constraints.NotNull.message}");
	}
}
