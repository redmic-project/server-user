package es.redmic.user.manager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.exception.custom.ResourceNotFoundException;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.BodyListDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.user.common.controller.ControllerRWImpl;
import es.redmic.user.common.query.QueryModel;
import es.redmic.user.manager.dto.UserChangeEmailDTO;
import es.redmic.user.manager.dto.UserChangeImageDTO;
import es.redmic.user.manager.dto.UserChangePasswordDTO;
import es.redmic.user.manager.dto.UserChangeSectorDTO;
import es.redmic.user.manager.dto.UserDTO;
import es.redmic.user.manager.dto.UserModulePermsDTO;
import es.redmic.user.manager.dto.UserNameDTO;
import es.redmic.user.manager.dto.UserResettingPasswordDTO;
import es.redmic.user.manager.dto.UserResettingRequestDTO;
import es.redmic.user.manager.model.User;
import es.redmic.user.manager.service.UserProfileService;

@RestController
@RequestMapping(value = "${controller.mapping.USER_PROFILE}")
public class UserProfileController extends ControllerRWImpl<UserDTO, User> {

	private UserProfileService service;

	@Autowired
	public UserProfileController(UserProfileService service) {
		super(service);
		this.service = service;
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public SuperDTO add(@Valid @RequestBody UserDTO dto, BindingResult bindingResult) {
		throw new ResourceNotFoundException();
	}

	/*
	 * Obtener un perfil de usuario con los accesso (S�lo el propio usuario al
	 * inicio)
	 */

	@Override
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public SuperDTO findAll(@ModelAttribute("querymodel") QueryModel querymodel,
			@RequestHeader(value = "Range", required = false) String rangeHeader, HttpServletResponse response) {
		String username = service.getUsername();
		UserModulePermsDTO profile = factory.map(service.findProfileByUsername(username), UserModulePermsDTO.class);
		List<UserModulePermsDTO> list = new ArrayList<UserModulePermsDTO>();
		list.add(profile);
		return new BodyListDTO<UserModulePermsDTO>(list);
	}

	/* Cambiar name de usuario */

	@RequestMapping(value = "/changeName/{id}", method = RequestMethod.PUT)
	public SuperDTO changeName(@Valid @RequestBody UserNameDTO dto, BindingResult bindingResult,
			@PathVariable("id") Long id, HttpServletResponse response) {

		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		dto.setId(id);
		service.changeName(dto);
		return new SuperDTO(true);
	}

	/* Cambiar sector de usuario */

	@RequestMapping(value = "/changeSector/{id}", method = RequestMethod.PUT)
	public SuperDTO changeSector(@Valid @RequestBody UserChangeSectorDTO dto, BindingResult bindingResult,
			@PathVariable("id") Long id, HttpServletResponse response) {

		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		dto.setId(id);
		service.changeSector(dto);
		return new SuperDTO(true);
	}

	/* Cambiar email de usuario */

	@RequestMapping(value = "/changeEmail/{id}", method = RequestMethod.PUT)
	public SuperDTO changeEmail(@Valid @RequestBody UserChangeEmailDTO dto, BindingResult bindingResult,
			@PathVariable("id") Long id, HttpServletResponse response) {

		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		dto.setId(id);
		service.changeEmail(dto);
		return new SuperDTO(true);
	}

	/* Cambiar image de usuario */

	@RequestMapping(value = "/changeImage/{id}", method = RequestMethod.PUT)
	public SuperDTO changeImage(@Valid @RequestBody UserChangeImageDTO dto, BindingResult bindingResult,
			@PathVariable("id") Long id, HttpServletResponse response) {

		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		dto.setId(id);
		service.changeImage(dto);
		return new SuperDTO(true);
	}

	/* Cambiar password de usuario (S�lo el propio usuario) */

	@RequestMapping(value = "/changePassword/{id}", method = RequestMethod.PUT)
	public SuperDTO changePassword(@Valid @RequestBody UserChangePasswordDTO dto, BindingResult bindingResult,
			HttpServletResponse response) {

		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		// username logueado
		String username = service.getUsername();
		User profile = service.findProfileByUsername(username);
		dto.setId(profile.getId());
		service.changePassword(dto);
		return new SuperDTO(true);
	}

	@RequestMapping(value = "/resettingRequest", method = RequestMethod.POST)
	public SuperDTO resettingRequest(@Valid @RequestBody UserResettingRequestDTO dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		service.resetPasswordRequest(dto.getEmail());
		return new SuperDTO(true);
	}

	@RequestMapping(value = "/resettingSetPassword", method = RequestMethod.POST)
	public SuperDTO resettingSetPassword(@Valid @RequestBody UserResettingPasswordDTO dto,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		service.resetPassword(dto);
		return new SuperDTO(true);
	}
}
