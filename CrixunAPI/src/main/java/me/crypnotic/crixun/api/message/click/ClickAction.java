package me.crypnotic.crixun.api.message.click;

public enum ClickAction {
	OPEN_URL {
		@Override
		public String toString() {
			return "open_url";
		}
	},
	OPEN_FILE {
		@Override
		public String toString() {
			return "open_file";
		}
	},
	RUN_COMMAND {
		@Override
		public String toString() {
			return "run_command";
		}
	},
	SUGGEST_COMMAND {
		@Override
		public String toString() {
			return "suggest_command";
		}
	},
	CHANGE_PAGE {
		@Override
		public String toString() {
			return "change_page";
		}
	};
}
