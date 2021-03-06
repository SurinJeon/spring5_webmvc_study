package spring5_webmvc_study.controller;

public class RegistRequest {

	private String email;
	private String password;
	private String confirmPassword;
	private String name;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isPasswordEqualToConfirmPassword() {
		return password.equals(confirmPassword);
	}
	@Override
	public String toString() {
		return String.format("RegistRequest [%s, %s, %s, %s]", email, password, confirmPassword, name);
	}
	
	
}
