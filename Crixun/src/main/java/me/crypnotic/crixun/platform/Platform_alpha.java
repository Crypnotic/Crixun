package me.crypnotic.crixun.platform;

import java.lang.reflect.Method;
import java.util.Optional;

import org.bukkit.entity.Player;

import me.crypnotic.crixun.api.IPlatform;
import me.crypnotic.crixun.api.PacketWrapper;
import me.crypnotic.crixun.api.message.MessageBuilder;
import me.crypnotic.crixun.api.utilities.Outcome;
import me.crypnotic.crixun.api.utilities.Reflections;

public class Platform_alpha implements IPlatform {

	@Override
	public PacketWrapper craftChatPacket(MessageBuilder builder) {
		Optional<Object> packet = Reflections.getNMSInstance("PacketPlayOutChat");
		if (!packet.isPresent()) {
			return new PacketWrapper(null);
		}

		Optional<Object> serializer = Reflections.getNMSInstance("IChatBaseComponent$ChatSerializer");
		if (!serializer.isPresent()) {
			return new PacketWrapper(null);
		}

		Optional<Method> a = Reflections.getMethod(serializer.get().getClass(), "a", String.class);
		if (!a.isPresent()) {
			return new PacketWrapper(null);
		}

		Optional<Object> payload = Reflections.invoke(a.get(), serializer.get(), builder.toString());
		if (!payload.isPresent()) {
			return new PacketWrapper(null);
		}

		PacketWrapper wrapper = new PacketWrapper(packet.get());

		wrapper.setValue("a", payload.get());
		wrapper.setValue("b", builder.getTarget().getData());

		return wrapper;
	}

	@Override
	public Outcome send(PacketWrapper wrapper, Player player) {
		Optional<Object> packet = wrapper.getPacket();
		if (!packet.isPresent()) {
			return Outcome.FAILURE;
		}

		Optional<Object> handle = Reflections.getHandle(player);
		if (!handle.isPresent()) {
			return Outcome.FAILURE;
		}

		Optional<Object> connection = Reflections.getValue(handle.get(), "playerConnection");
		if (!connection.isPresent()) {
			return Outcome.FAILURE;
		}

		Optional<Method> send = Reflections.getMethod(connection.get().getClass(), "sendPacket");
		if (!send.isPresent()) {
			return Outcome.FAILURE;
		}

		Optional<Object> invoke = Reflections.invoke(send.get(), connection.get(), wrapper.getPacket().get());

		return Outcome.parse(invoke.isPresent());
	}
}
