package org.school.userandsecurity.service.bo;

import java.util.Map;

import org.school.userandsecurity.vo.UserCredentialsVO;

public interface AuthenticationService {

	Map<String, Object> authenticateUser(UserCredentialsVO userVO);

}
