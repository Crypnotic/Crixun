package me.crypnotic.crixun.api.message.hover;

public enum HoverAction {
	SHOW_TEXT {
		@Override
		public String toString() {
			return "show_text";
		}
	},
	SHOW_ACHIEVEMENT {
		@Override
		public String toString() {
			return "show_achievement";
		}
	},
	SHOW_ITEM {
		@Override
		public String toString() {
			return "show_item";
		}
	},
	SHOW_ENTITY {
		@Override
		public String toString() {
			return "show_entity";
		}
	};
}
