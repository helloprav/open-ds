/**
 * 
 */
package org.school.userandsecurity.service.bo.impl;

import java.util.List;

import javax.inject.Inject;

import org.openframework.common.rest.service.impl.BaseServiceImpl;
import org.school.userandsecurity.service.adaptor.FunctionAdaptor;
import org.school.userandsecurity.service.as.FunctionAS;
import org.school.userandsecurity.service.bo.FunctionService;
import org.school.userandsecurity.vo.FunctionVO;
import org.springframework.stereotype.Service;

/**
 * @author Java Developer
 *
 */
@Service
public class FunctionServiceImpl extends BaseServiceImpl implements FunctionService {

	@Inject
	private FunctionAS functionAS;

	@Inject
	private FunctionAdaptor functionAdaptor;

	@Override
	public List<FunctionVO> findFunctions() {
		return functionAdaptor.toFunctionVO(functionAS.findFunctions());
	}

}
