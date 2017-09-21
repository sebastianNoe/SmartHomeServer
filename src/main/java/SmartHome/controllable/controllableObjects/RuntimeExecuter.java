package SmartHome.controllable.controllableObjects;

import java.io.File;
import java.io.IOException;

public class RuntimeExecuter {

	public void execute(String statement, File location) throws IOException {
		Runtime.getRuntime().exec(statement, null, location);
	}
}
