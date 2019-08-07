package com.seeka.app.dto;

public class EnglishScoresDto {

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
     * @param englishType
     *            the englishType to set
     */
    public void setEnglishType(String englishType) {
        this.englishType = englishType;
    }

    /**
     * @return the reading
     */
    public Double getReading() {
        return reading;
    }

    /**
     * @param reading
     *            the reading to set
     */
    public void setReading(Double reading) {
        this.reading = reading;
    }

    /**
     * @return the writing
     */
    public Double getWriting() {
        return writing;
    }

    /**
     * @param writing
     *            the writing to set
     */
    public void setWriting(Double writing) {
        this.writing = writing;
    }

    /**
     * @return the speaking
     */
    public Double getSpeaking() {
        return speaking;
    }

    /**
     * @param speaking
     *            the speaking to set
     */
    public void setSpeaking(Double speaking) {
        this.speaking = speaking;
    }

    /**
     * @return the listening
     */
    public Double getListening() {
        return listening;
    }

    /**
     * @param listening
     *            the listening to set
     */
    public void setListening(Double listening) {
        this.listening = listening;
    }

    /**
     * @return the overall
     */
    public Double getOverall() {
        return overall;
    }

    /**
     * @param overall
     *            the overall to set
     */
    public void setOverall(Double overall) {
        this.overall = overall;
    }

}
