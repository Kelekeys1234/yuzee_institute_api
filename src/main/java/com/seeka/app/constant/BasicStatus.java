package com.seeka.app.constant;

public interface BasicStatus<T extends Enum<?>> {

	String getStatusValue();

	BasicStatus<T>[] nextStatus();

}
