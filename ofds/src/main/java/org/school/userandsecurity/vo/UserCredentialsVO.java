/**
 * 
 */
package org.school.userandsecurity.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * @author Java Developer
 *
 */
public class UserCredentialsVO {

	@Email(message="email should be an valid email")
	@Length(max=50, message="length should not be more than 50 characters")
	private String email;

	@Length(max=50, message="length should not be more than 50 characters")
	private String mobile;

	@NotNull(message = "Password is required")
	@Size(min=5, max=50, message="The length should be between 5 and 50 characters long")
	private char[] password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

}
