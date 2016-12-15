package me.crypnotic.crixun.api;

import java.util.Optional;

import org.bukkit.entity.Player;

import me.crypnotic.crixun.api.utilities.Outcome;
import me.crypnotic.crixun.api.utilities.Reflections;

public class PacketWrapper {

	private static IPlatform platform = Reflections.getPlatform();

	private Object packet;

	public PacketWrapper(Object packet) {
		this.packet = packet;
	}

	public Optional<Object> getPacket() {
		return Optional.of(packet);
	}

	public Optional<Object> getValue(String name) {
		return Reflections.getValue(packet, name);
	}

	public Outcome setValue(String name, Object value) {
		return Reflections.setValue(packet, name, value);
	}

	public Outcome send(Player player) {
		return platform.send(this, player);
	}
}
