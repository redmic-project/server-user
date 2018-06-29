package es.redmic.user.unit.manager.dto;

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
