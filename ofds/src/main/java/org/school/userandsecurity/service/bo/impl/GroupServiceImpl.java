/**
 * 
 */
package org.school.userandsecurity.service.bo.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.openframework.common.rest.service.impl.BaseServiceImpl;
import org.school.userandsecurity.service.adaptor.GroupAdaptor;
import org.school.userandsecurity.service.as.GroupAS;
import org.school.userandsecurity.service.bo.GroupService;
import org.school.userandsecurity.service.entity.Group;
import org.school.userandsecurity.vo.GroupVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Java Developer
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackForClassName = "Exception", isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class GroupServiceImpl extends BaseServiceImpl implements GroupService {

	@SuppressWarnings("unused")
	@Inject
	private GroupAS groupAS;

	@SuppressWarnings("unused")
	@Inject
	private GroupAdaptor groupAdaptor;

	@Override
	public List<GroupVO> findGroups() {
		return groupAdaptor.toGroupVO(groupAS.findGroups());
	}

	@Override
	public List<GroupVO> findGroupsByStatus(Boolean status) {
		return groupAdaptor.toGroupVO(groupAS.findGroupsByStatus(status));
	}

	@Override
	public Long createGroup(GroupVO groupVO) {

		Group groupTobeCreated = groupAdaptor.fromVO(groupVO);
		groupTobeCreated.setId(null);
		groupAS.checkUniqueGroupName(groupTobeCreated.getGroupName());
		groupTobeCreated.setCreatedDate(new Date());
		groupTobeCreated = groupAS.saveGroup(groupTobeCreated);
		return groupTobeCreated.getId();
	}

	@Override
	public GroupVO findGroupById(Long id) {
		return groupAdaptor.toVO(groupAS.findGroupById(id));
	}

	@Override
	public GroupVO updateGroup(GroupVO groupVO) {

		Group groupTobeUpdated = groupAdaptor.fromVO(groupVO);
		groupTobeUpdated.setModifiedDate(new Date());
		groupAS.checkUniqueGroupNameNotId(groupTobeUpdated);
		groupAS.deleteGroupFunctions(groupVO.getId());
		return groupAdaptor.toVO(groupAS.saveGroup(groupTobeUpdated));
	}

	@Override
	public GroupVO updateStatus(GroupVO groupVO) {
		// TODO Auto-generated method stub
		return null;
	}
}
