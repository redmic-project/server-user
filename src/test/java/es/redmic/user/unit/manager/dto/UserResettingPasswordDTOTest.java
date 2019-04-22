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

import es.redmic.user.manager.dto.UserResettingPasswordDTO;

public class UserResettingPasswordDTOTest extends DTOBaseTest<UserResettingPasswordDTO>{

	private static UserResettingPasswordDTO dto = new UserResettingPasswordDTO();

	@Before
	public void beforeTest() {
		
		dto.setPassword("password");
		dto.setToken("token");
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
	public void validationDTO_ReturnNotNullError_IfTokenIsNull() {
		
		dto.setToken(null);

		checkDTOHasError(dto, "token", "{javax.validation.constraints.NotNull.message}");
	}
}
