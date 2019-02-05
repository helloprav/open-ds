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

	@NotNull(message = "Username is required")
	@Length(max=50, message="length should not be more than 50 characters")
	private String username;

	@NotNull(message = "Password is required")
	@Size(min=5, max=50, message="The length should be between 5 and 50 characters long")
	private char[] password;

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
