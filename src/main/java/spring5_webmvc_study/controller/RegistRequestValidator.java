package spring5_webmvc_study.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RegistRequestValidator implements Validator {
	
	// 정규표현식 사용한 문자 사용 범위
	private static final String emailRegExp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;

	
	public RegistRequestValidator() {
		this.pattern = Pattern.compile(emailRegExp); // pattern 설정해줌
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return RegistRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// email검증
		RegistRequest regReq = (RegistRequest) target;
		if(regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()) {
			errors.rejectValue("email", "required");
		} else {
			Matcher matcher = pattern.matcher(regReq.getEmail());
			if(!matcher.matches()) {
				errors.rejectValue("email", "bad");
			}
		}
		
		// name검증
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		
		// password 검증
		ValidationUtils.rejectIfEmpty(errors, "password", "required");
		
		// confirmPassword 검증
		ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required");
		
		if(!regReq.getPassword().isEmpty()) {
			if(!regReq.isPasswordEqualToConfirmPassword()) {
				errors.rejectValue("confirmPassword", "nomatch");
			}
		}
	}

}
