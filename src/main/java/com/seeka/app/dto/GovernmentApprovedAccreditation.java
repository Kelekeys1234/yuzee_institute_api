package com.seeka.app.dto;

import java.io.Serializable;

public class GovernmentApprovedAccreditation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String accreditationName;

	private String websiteLinkOfAccreditation;

	private String accreditationLogo;

	public String getAccreditationName() {
		return accreditationName;
	}

	public void setAccreditationName(String accreditationName) {
		this.accreditationName = accreditationName;
	}

	public String getWebsiteLinkOfAccreditation() {
		return websiteLinkOfAccreditation;
	}

	public void setWebsiteLinkOfAccreditation(String websiteLinkOfAccreditation) {
		this.websiteLinkOfAccreditation = websiteLinkOfAccreditation;
	}

	public String getAccreditationLogo() {
		return accreditationLogo;
	}

	public void setAccreditationLogo(String accreditationLogo) {
		this.accreditationLogo = accreditationLogo;
	}
}
