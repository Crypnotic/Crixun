package me.crypnotic.crixun.api.message.hover;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HoverComponent {

	private HoverAction action;
	private String value;
}
