package org.school.userandsecurity.service.adaptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.school.userandsecurity.service.entity.Function;
import org.school.userandsecurity.service.entity.Group;
import org.school.userandsecurity.service.entity.GroupFunction;
import org.school.userandsecurity.vo.FunctionVO;
import org.school.userandsecurity.vo.GroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class GroupAdaptor {

	public List<GroupVO> toGroupVO(List<Group> groupList) {

		List<GroupVO> productCategoryVOList = new ArrayList<>();
		ListIterator<Group> listIterator = groupList.listIterator();
		while (listIterator.hasNext()) {
			Group productCategory = listIterator.next();
			GroupVO productCategoryVO = toVO(productCategory);
			productCategoryVOList.add(productCategoryVO);
		}
		return productCategoryVOList;
	}

	public GroupVO toVO(Group group) {
		GroupVO groupVO = new GroupVO();
		BeanUtils.copyProperties(group, groupVO, "groupFunctions");
		toFunctionVO(group.getGroupFunctions(), groupVO);
		return groupVO;
	}

	private void toFunctionVO(List<GroupFunction> groupFunctions, GroupVO groupVO) {

		List<FunctionVO> functionVOList = new ArrayList<>();
		Iterator<GroupFunction> iterator = groupFunctions.iterator();
		while (iterator.hasNext()) {
			GroupFunction groupFunction = iterator.next();
			System.out.println(groupFunction.getId());
			FunctionVO functionVO = new FunctionVO();
			if(null!=groupFunction.getFunction()) {
				functionVO.setId(groupFunction.getFunction().getId());
				functionVO.setName(groupFunction.getFunction().getName());
			}
			functionVOList.add(functionVO);
		}
		groupVO.setFunctionList(functionVOList);
	}

	public Group fromVO(GroupVO groupVO) {

		Group group = new Group();
		BeanUtils.copyProperties(groupVO, group);
		fromFunctionVOList(groupVO.getFunctionList(), group);
		return group;
	}

	private void fromFunctionVOList(List<FunctionVO> functionVOList, Group group) {

		if(null == functionVOList) {
			return ;
		}
		List<GroupFunction> groupFunctionList = new ArrayList<>();
		Iterator<FunctionVO> iterator = functionVOList.iterator();
		while (iterator.hasNext()) {

			FunctionVO functionVO = iterator.next();
			GroupFunction groupFunction = new GroupFunction();
			Function function = new Function();
			function.setId(functionVO.getId());
			groupFunction.setFunction(function);
			groupFunction.setGroup(group);
			groupFunctionList.add(groupFunction);
		}
		group.setGroupFunctions(groupFunctionList);
	}

}
