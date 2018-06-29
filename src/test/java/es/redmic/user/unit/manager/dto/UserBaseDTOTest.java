package es.redmic.user.unit.manager.dto;

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
