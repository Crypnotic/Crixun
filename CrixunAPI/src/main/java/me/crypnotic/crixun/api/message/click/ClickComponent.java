package me.crypnotic.crixun.api.message.click;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClickComponent {

	private ClickAction action;
	private String value;
}
