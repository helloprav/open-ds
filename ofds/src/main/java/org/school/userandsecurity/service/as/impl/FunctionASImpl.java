package org.school.userandsecurity.service.as.impl;

import java.util.List;

import javax.inject.Inject;

import org.school.userandsecurity.service.as.FunctionAS;
import org.school.userandsecurity.service.entity.Function;
import org.school.userandsecurity.service.repository.FunctionRepository;
import org.springframework.stereotype.Service;

@Service
public class FunctionASImpl extends BaseASImpl implements FunctionAS {

	@Inject
	private FunctionRepository functionRepository;

	@Override
	public List<Function> findFunctions() {
		return functionRepository.findAll();
	}

}
