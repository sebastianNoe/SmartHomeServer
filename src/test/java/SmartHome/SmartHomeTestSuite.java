package SmartHome;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import SmartHome.controllable.controllableObjects.RadioSocketTest;
import SmartHome.services.CommandExecutionTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	RadioSocketTest.class,
	CommandExecutionTest.class
})
public class SmartHomeTestSuite {

}
