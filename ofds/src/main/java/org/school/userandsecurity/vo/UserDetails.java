/**
 * 
 */
package org.school.userandsecurity.vo;

import org.openframework.common.rest.vo.BaseVO;
import org.openframework.common.rest.vo.UserVO;

/**
 * @author Java Developer
 *
 */
public class UserDetails extends BaseVO {

	private UserVO userVO;
	private UserAccess userAccess;

	public UserVO getUserVO() {
		return userVO;
	}
	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	public UserAccess getUserAccess() {
		return userAccess;
	}
	public void setUserAccess(UserAccess userAccess) {
		this.userAccess = userAccess;
	}
}
