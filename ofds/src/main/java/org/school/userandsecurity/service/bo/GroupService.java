/**
 * 
 */
package org.school.userandsecurity.service.bo;

import java.util.List;

import org.school.userandsecurity.vo.GroupVO;

/**
 * @author Java Developer
 *
 */
public interface GroupService {

	List<GroupVO> findGroups();

	List<GroupVO> findGroupsByStatus(Boolean status);

	Long createGroup(GroupVO groupVO);

	GroupVO findGroupById(Long id);

	GroupVO updateGroup(GroupVO group);

	GroupVO updateStatus(GroupVO groupVO);
}
