/**
 * 
 */
package org.school.userandsecurity.service.repository;

import java.util.List;

import org.school.userandsecurity.enums.UserRole;
import org.school.userandsecurity.enums.UserStatus;
import org.school.userandsecurity.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author pmis30
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Refer
	 * [http://stackoverflow.com/questions/2123438/hibernate-how-to-set-null-query-parameter-value-with-hql]
	 * for null parameter at runtime
	 * 
	 * @return
	 */

    List<User> findByRole(@Param("role") UserRole role);

    List<User> findByRoleAndStatus(@Param("role") UserRole role, @Param("userStatus") UserStatus userStatus);

    @Query("from User u where u.admissionNo = :admissionNo")
	List<User> admissionNoExists(@Param("admissionNo") Long admissionNo);

	List<User> findByEmail(@Param("email") String email);

	@Query("from User u left join fetch u.userGroups ug join fetch ug.group where u.id=:id")
	User findUserGroupsAndFunctions(@Param("id") Long id);

    //@Query("from User u left join fetch u left join fetch u.userRoles ur where ur.role.roleName=:role ")
	//@Query("from User u where ur.role.roleName=:role ")
    //List<User> findByRole(@Param("role") String role);

	//@Query("from User pc where pc.parentCategory.id is NULL")
	//List<User> findUsers();
/*
	//@Query("from User pc where pc.parentCategory.id = :parentId")
	List<User> findUserById(@Param(value = "parentId") Long parentId);

	//@Query("from User pc where pc.isValid = :status")
	List<User> findCategoriesByStatus(@Param("status") Boolean status);

	//@Query("from User pc where pc.parentCategory.id = :parentId and pc.isValid = :status")
	List<User> findCategoriesByParentIdAndStatus(@Param(value = "parentId") Long parentId, @Param("status") Boolean status);

	//@Query("from User pc where pc.id = :id")
	User findOne(@Param(value = "id") Long id);

	//@Query("from User pc where pc.id = :id and pc.isLeaf = :isLeaf")
	User findOne(@Param(value = "id") Long id, @Param(value = "isLeaf") Boolean isLeaf);

	@Modifying
	//@Query("update User pc set pc.name = :name, pc.description=:description,  pc.modifiedBy.id = :userId, pc.modifiedDate = :modifiedDate, pc.parentCategory.id = :parentId where pc.id=:id")
	void update(@Param("id") Long id, @Param(value = "parentId") Long parentId, @Param("name") String name,
			@Param("description") String description, @Param("userId") Long userId,
			@Param("modifiedDate") Date modifiedDate);

	@Modifying
	//@Query("update User p set p.isValid = :isValid, p.modifiedBy.id = :userId, p.modifiedDate = :modifiedDate where p.id=:id")
	void updateStatus(@Param("id") Long id, @Param("isValid") Boolean isValid, @Param("userId") Long userId,
			@Param("modifiedDate") Date modifiedDate);
*/
}
