package org.school.userandsecurity.service.repository;

import java.util.List;

import org.school.userandsecurity.service.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Long> {

	@Query("from Group g where g.groupName = :groupName")
	Group findByGroupName(@Param("groupName") String groupName);

	@Query("from Group g where g.isValid = :isValid")
	List<Group> findByIsValid(@Param("isValid") Boolean isValid);

}
