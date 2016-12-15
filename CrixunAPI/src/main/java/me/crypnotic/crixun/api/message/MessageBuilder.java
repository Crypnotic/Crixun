package me.crypnotic.crixun.api.message;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;
import me.crypnotic.crixun.api.IPlatform;
import me.crypnotic.crixun.api.PacketWrapper;
import me.crypnotic.crixun.api.message.click.ClickComponent;
import me.crypnotic.crixun.api.message.hover.HoverComponent;
import me.crypnotic.crixun.api.utilities.Reflections;
import me.crypnotic.crixun.api.utilities.Strings;

public class MessageBuilder {

	private static IPlatform platform = Reflections.getPlatform();
	private static Gson gson = new Gson();

	@Getter
	private String text;
	@Getter
	@Setter
	private ClickComponent clickEvent;
	@Getter
	@Setter
	private HoverComponent hoverEvent;
	@Getter
	@Setter
	private transient MessageTarget target;

	public MessageBuilder() {
		this.target = MessageTarget.CHAT;
	}

	public void setText(String text) {
		this.text = Strings.color(text);
	}

	public PacketWrapper craft() {
		return platform.craftChatPacket(this);
	}

	@Override
	public String toString() {
		return gson.toJson(this);
	}
}
