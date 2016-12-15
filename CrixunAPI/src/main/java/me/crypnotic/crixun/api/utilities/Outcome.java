package me.crypnotic.crixun.api.utilities;

public enum Outcome {
	SUCCESSFUL, FAILURE, UNKNOWN;

	public Boolean isSuccessful() {
		return (this == Outcome.SUCCESSFUL);
	}

	public String toString() {
		return super.toString().substring(0, 1).toUpperCase() + super.toString().substring(1).toLowerCase();
	}

	public static Outcome parse(boolean condition) {
		return (condition) ? Outcome.SUCCESSFUL : Outcome.FAILURE;
	}
}
