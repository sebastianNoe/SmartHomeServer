package SmartHome;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import SmartHome.exceptions.CorruptSettingsFile;
import SmartHome.exceptions.SettingsNotSetException;

@SpringBootApplication
@ComponentScan({ "SmartHome.controllable", "SmartHome.controllable.command",
		"SmartHome.controllable.controllableObjects", "SmartHome.services", "SmartHome" })
public class Application {
	private static File settingsLocation;

	public static void main(String[] args) throws Throwable {
		deriveSettingsLocation(args);
		SpringApplication.run(Application.class, args);
	}

	public static void deriveSettingsLocation(String[] args) throws CorruptSettingsFile {
		try {
			settingsLocation = checkArgumentsForSettings(args);
		} catch (SettingsNotSetException e) {
			System.out.println("Please provide location of settings.xml:");
			settingsLocation = new File(System.console().readLine());
		}
		if (!settingsLocation.exists()) {
			throw new CorruptSettingsFile();
		}
	}

	public static File checkArgumentsForSettings(String[] args) throws SettingsNotSetException {
		boolean isNextSettingsPath = false;
		for (int i = 0; i < args.length; i++) {
			if (isNextSettingsPath) {
				return new File(args[i]);
			} else if (args[i].equals("-s")) {
				isNextSettingsPath = true;
			}
		}

		throw new SettingsNotSetException();
	}

	public static File getSettingsLocation() {
		return settingsLocation;
	}
}