package es.redmic.user.manager.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;



public class FeedbackDTO {

	@NotNull
	@Email
	private String email;

	@NotNull
	private String name;

	@NotNull
	private String subject;

	private String message;

	private String codeError;

	public FeedbackDTO() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCodeError() {
		return codeError;
	}

	public void setCodeError(String codeError) {
		this.codeError = codeError;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
