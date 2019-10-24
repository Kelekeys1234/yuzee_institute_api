package com.seeka.app.dto;

import java.io.Serializable;

public class EnglishScoresDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8118493380744357383L;
	private String englishType;
	private Double reading;
	private Double writing;
	private Double speaking;
	private Double listening;
	private Double overall;

	/**
	 * @return the englishType
	 */
	public String getEnglishType() {
		return englishType;
	}

	/**
	 * @param englishType the englishType to set
	 */
	public void setEnglishType(final String englishType) {
		this.englishType = englishType;
	}

	/**
	 * @return the reading
	 */
	public Double getReading() {
		return reading;
	}

	/**
	 * @param reading the reading to set
	 */
	public void setReading(final Double reading) {
		this.reading = reading;
	}

	/**
	 * @return the writing
	 */
	public Double getWriting() {
		return writing;
	}

	/**
	 * @param writing the writing to set
	 */
	public void setWriting(final Double writing) {
		this.writing = writing;
	}

	/**
	 * @return the speaking
	 */
	public Double getSpeaking() {
		return speaking;
	}

	/**
	 * @param speaking the speaking to set
	 */
	public void setSpeaking(final Double speaking) {
		this.speaking = speaking;
	}

	/**
	 * @return the listening
	 */
	public Double getListening() {
		return listening;
	}

	/**
	 * @param listening the listening to set
	 */
	public void setListening(final Double listening) {
		this.listening = listening;
	}

	/**
	 * @return the overall
	 */
	public Double getOverall() {
		return overall;
	}

	/**
	 * @param overall the overall to set
	 */
	public void setOverall(final Double overall) {
		this.overall = overall;
	}

}
