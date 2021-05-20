package spring5_webmvc_study.controller;

@SuppressWarnings("serial")
public class WrongIdPasswordException extends RuntimeException {

	public WrongIdPasswordException() {
	}

	public WrongIdPasswordException(String message) {
		super(message);
	}

}
