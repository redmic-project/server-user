package es.redmic.user.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.redmic.user.common.service.ServiceRWImpl;
import es.redmic.user.manager.dto.ModuleDTO;
import es.redmic.user.manager.model.Module;
import es.redmic.user.manager.repository.ModuleRepository;

@Service
public class ModuleService extends ServiceRWImpl<Module, ModuleDTO> {

	ModuleRepository repository;
	
	@Autowired
	public ModuleService(ModuleRepository repository) {
		super(repository);
		this.repository = repository;
	}
	
	public List<Object> getOpenModules() {
		
		return repository.findOpenModules();
	}
}