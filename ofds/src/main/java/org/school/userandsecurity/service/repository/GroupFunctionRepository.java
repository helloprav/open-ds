package org.school.userandsecurity.service.repository;

import org.school.userandsecurity.service.entity.GroupFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//TODO Understand why this @Transactional is required
//@Transactional(propagation= Propagation.REQUIRED)
public interface GroupFunctionRepository extends JpaRepository<GroupFunction, Long> {

	@Modifying
    @Query("delete from GroupFunction gf where gf.group.id=:groupID")
    void deleteByGroupID(@Param("groupID") long groupID);

}
