package me.crypnotic.crixun.api.utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

import org.bukkit.Bukkit;

import me.crypnotic.crixun.api.IPlatform;

public class Reflections {

	private static IPlatform platform;
	private static String version;

	public static void setPlatform(IPlatform platform) throws IllegalAccessException {
		if (Reflections.platform != null) {
			throw new IllegalAccessException("Cannot redefine Reflections IPlatform reference");
		}
		Reflections.platform = platform;
	}

	public static IPlatform getPlatform() {
		return platform;
	}

	public static String getVersion() {
		if (version == null || version.isEmpty()) {
			version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		}
		return version;
	}

	public static String getFriendlyVersion() {
		String version = getVersion();
		return version.substring(0, version.length() - 1);
	}

	public static Optional<Class<?>> getNMSClass(String name) {
		try {
			Class<?> klass = Class.forName("net.minecraft.server." + getVersion() + name);
			return Optional.of(klass);
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<Object> getNMSInstance(String name) {
		try {
			Optional<Class<?>> klass = getNMSClass(name);
			if (klass.isPresent()) {
				return Optional.of(klass.get().newInstance());
			} else {
				return Optional.empty();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<Method> getMethod(Class<?> klass, String name, Class<?>... arguments) {
		for (final Method method : klass.getMethods()) {
			if (method.getName().equals(name)) {
				if (arguments.length > 0 && arguments.length == method.getParameterCount()) {
					boolean equal = true;
					parameterCheck: {
						Class<?>[] parameters = method.getParameterTypes();
						for (int i = 0; i < parameters.length; i++) {
							if (parameters[i] != arguments[i]) {
								equal = false;
								break parameterCheck;
							}
						}
					}
					if (equal) {
						method.setAccessible(true);
						return Optional.of(method);
					}
				} else {
					return Optional.of(method);
				}
			}
		}
		return Optional.empty();
	}

	public static Optional<Field> getField(Class<?> klass, String name) {
		try {
			Field field = klass.getDeclaredField(name);
			if (field != null) {
				field.setAccessible(true);
			}
			return Optional.of(field);
		} catch (Exception exception) {
			return Optional.empty();
		}
	}

	public static Optional<Object> getValue(Object object, String name) {
		Optional<Field> field = Reflections.getField(object.getClass(), name);
		if (!field.isPresent()) {
			return Optional.empty();
		}

		try {
			return Optional.of(field.get().get(object));
		} catch (Exception exception) {
			return Optional.empty();
		}
	}

	public static Outcome setValue(Object object, String name, Object value) {
		Optional<Field> field = Reflections.getField(object.getClass(), name);
		if (!field.isPresent()) {
			return Outcome.FAILURE;
		}

		try {
			field.get().set(object, value);
			return Outcome.SUCCESSFUL;
		} catch (Exception exception) {
			return Outcome.FAILURE;
		}
	}

	public static Optional<Object> invoke(Method method, Object object, Object... arguments) {
		try {
			Object result = method.invoke(object, arguments);
			if (method.getReturnType() != null && result != null) {
				return Optional.of(result);
			} else {
				return Optional.of(new Object());
			}
		} catch (Exception exception) {
			return Optional.empty();
		}
	}

	public static Optional<Object> getHandle(Object object) {
		try {
			if (object == null) {
				return Optional.empty();
			}
			Optional<Method> getHandle = getMethod(object.getClass(), "getHandle", new Class[0]);
			return Optional.of(getHandle.get().invoke(object, new Object[0]));
		} catch (Exception exception) {
			return Optional.empty();
		}
	}
}
