package me.crypnotic.crixun.api.message;

import lombok.Getter;

public enum MessageTarget {
	SYSTEM((byte) 0), CHAT((byte) 1), ACTION_BAR((byte) 2);

	@Getter
	private byte data;

	MessageTarget(byte data) {
		this.data = data;
	}
}
