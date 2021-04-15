package com.yuzee.app.dto;

import java.io.Serializable;
import java.util.Map;

public class EmailPayloadDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1943945572112639437L;
	private String templateName;
	private Map<String, String> templateParameters;
	private String toaddress;
	private String subject;
	private String body;
	private String deviceId;

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Map<String, String> getTemplateParameters() {
		return templateParameters;
	}

	public void setTemplateParameters(Map<String, String> templateParameters) {
		this.templateParameters = templateParameters;
	}

	/**
	 * @return the toaddress
	 */
	public String getToaddress() {
		return toaddress;
	}

	/**
	 * @param toaddress the toaddress to set
	 */
	public void setToaddress(String toaddress) {
		this.toaddress = toaddress;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((templateName == null) ? 0 : templateName.hashCode());
		result = prime * result + ((templateParameters == null) ? 0 : templateParameters.hashCode());
		result = prime * result + ((toaddress == null) ? 0 : toaddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailPayloadDto other = (EmailPayloadDto) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (templateName == null) {
			if (other.templateName != null)
				return false;
		} else if (!templateName.equals(other.templateName))
			return false;
		if (templateParameters == null) {
			if (other.templateParameters != null)
				return false;
		} else if (!templateParameters.equals(other.templateParameters))
			return false;
		if (toaddress == null) {
			if (other.toaddress != null)
				return false;
		} else if (!toaddress.equals(other.toaddress))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailPayloadDto [templateName=").append(templateName).append(", templateParameters=")
				.append(templateParameters).append(", toaddress=").append(toaddress).append(", subject=")
				.append(subject).append(", body=").append(body).append("]");
		return builder.toString();
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
