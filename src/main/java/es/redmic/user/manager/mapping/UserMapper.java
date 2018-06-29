package es.redmic.user.manager.mapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.redmic.user.manager.dto.ModulePermsDTO;
import es.redmic.user.manager.dto.ModuleStructureDTO;
import es.redmic.user.manager.dto.UserModulePermsDTO;
import es.redmic.user.manager.model.Access;
import es.redmic.user.manager.model.Module;
import es.redmic.user.manager.model.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

@Component
public class UserMapper extends CustomMapper<User, UserModulePermsDTO> {

	@Override
	public void mapAtoB(User a, UserModulePermsDTO b, MappingContext context) {
		// devolver categorias y modulos a los que puede acceder

		List<ModuleStructureDTO> categories = new ArrayList<ModuleStructureDTO>();

		for (Access access : a.getAccesses()) {
			Module module = access.getModule();
			if (module.getParent() != null) {

				module = module.getParent();

				if (module.getParent() != null)
					module = module.getParent(); // TODO: enviar anidado los
													// nietos
			}

			ModuleStructureDTO eachCategory = mapperFacade.map(module, ModuleStructureDTO.class);
			eachCategory.setPerms(access.getPerms());
			Integer exist = categories.indexOf(eachCategory);
			if (exist == -1)
				categories.add(eachCategory);
			else
				eachCategory = categories.get(exist);

			if (access.getModule().getId() != eachCategory.getId()) {
				// seteo el modulo
				ModulePermsDTO modulePerms = mapperFacade.map(access.getModule(), ModulePermsDTO.class);
				if (access.getPerms() > 0 && modulePerms.getEnable()) {
					modulePerms.setPerms(access.getPerms());
					eachCategory.setModule(modulePerms);
				}
			}

		}
		b.setCategoryBean(categories);

	}

	@Override
	public void mapBtoA(UserModulePermsDTO a, User b, MappingContext context) {
	}

}
