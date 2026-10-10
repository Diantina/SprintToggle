package diantina.sprinttoggle.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class SprintToggleClient implements ClientModInitializer {
	
	public static boolean sprintEnabled = true;

	private static  final Path CONFIG_FILE =
					FabricLoader.getInstance()
							.getConfigDir()
							.resolve("sprinttoggle.properties");
	
	@Override
	public void onInitializeClient() {
		loadConfig();
	}

	public static void setSprintEnabled(boolean enabled) {
		sprintEnabled = enabled;
		saveConfig();
	}

	private static void loadConfig() {
		if (!Files.exists(CONFIG_FILE)) {
			return;
		}

		Properties properties = new Properties();

		try (Reader reader = Files.newBufferedReader(
			CONFIG_FILE,
			StandardCharsets.UTF_8
		)) {
			properties.load(reader);

			String value = properties.getProperty("sprintEnabled");

			if (value != null) {
				sprintEnabled = Boolean.parseBoolean(value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void saveConfig() {
		Properties properties = new Properties();
		properties.setProperty("sprintEnabled", Boolean.toString(sprintEnabled));

		try {
			Files.createDirectories(CONFIG_FILE.getParent());

			try (Writer writer = Files.newBufferedWriter(
				CONFIG_FILE,
				StandardCharsets.UTF_8
			)) {
				properties.store(writer, "SprintToggle configuration");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}