package es.redmic.user.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import es.redmic.user.manager.model.Access;
import es.redmic.user.manager.model.AccessPK;
import es.redmic.user.manager.model.User;

public interface AccessRepository extends JpaRepository<Access, AccessPK>,
		QuerydslPredicateExecutor<Access> {
	List<Access> findByUser(User user);
}
