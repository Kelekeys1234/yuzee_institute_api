package com.yuzee.app.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailAddressDto implements Serializable {
	
	private static final long serialVersionUID = 2675179582281264241L;

	@NotEmpty(message = "to_user is required")
	@JsonProperty("to_user")
	private String toUser;
	
	@NotEmpty(message = "to_email is required")
	@JsonProperty("to_email")
	private String toEmail;
	
	@JsonProperty("cc")
	private List<String> cc;
	
	@JsonProperty("bcc")
	private List<String> bcc;
}
