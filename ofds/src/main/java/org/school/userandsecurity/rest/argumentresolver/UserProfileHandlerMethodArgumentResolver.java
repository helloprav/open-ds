package org.school.userandsecurity.rest.argumentresolver;

import org.openframework.common.rest.argumentresolver.AbstractUserProfileHandlerMethodArgumentResolver;
import org.school.userandsecurity.utils.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProfileHandlerMethodArgumentResolver extends AbstractUserProfileHandlerMethodArgumentResolver {

	@Autowired
	private EncryptionUtil encryptionUtil;

	@Override
	public String decrypt(String value) {
		return encryptionUtil.decrypt(value);
	}

}
