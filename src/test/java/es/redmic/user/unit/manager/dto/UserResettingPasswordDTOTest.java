package es.redmic.user.unit.manager.dto;

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
