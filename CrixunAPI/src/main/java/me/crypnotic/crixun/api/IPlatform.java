package me.crypnotic.crixun.api;

import org.bukkit.entity.Player;

import me.crypnotic.crixun.api.message.MessageBuilder;
import me.crypnotic.crixun.api.utilities.Outcome;

public interface IPlatform {

	PacketWrapper craftChatPacket(MessageBuilder builder);

	Outcome send(PacketWrapper packet, Player player);
}
