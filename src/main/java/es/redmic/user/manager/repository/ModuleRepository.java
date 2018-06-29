package es.redmic.user.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import es.redmic.databaselib.common.repository.BaseRepository;
import es.redmic.user.manager.model.Module;

public interface ModuleRepository extends BaseRepository<Module, Long> {

	@Query(value = "SELECT (SELECT name FROM app.module WHERE m.parentid = id)"+
		" as parentName, name as name  FROM app.module m WHERE open = true;", nativeQuery = true)
	List<Object> findOpenModules();
}
