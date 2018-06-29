package es.redmic.user.unit.manager.dto;

import org.junit.Before;
import org.junit.Test;

import es.redmic.user.manager.dto.UserResettingRequestDTO;

public class UserResettingRequestDTOTest extends DTOBaseTest<UserResettingRequestDTO> {

	private static UserResettingRequestDTO dto = new UserResettingRequestDTO();

	@Before
	public void beforeTest() {
		
		dto.setEmail("email@test.com");
	}

	@Test
	public void validationDTO_NoReturnError_IfDTOIsCorrect() {
		
		checkDTOHasNoError(dto);
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
