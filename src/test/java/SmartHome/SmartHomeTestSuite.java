package smartHome;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import smartHome.controllable.ControllableManagerTest;
import smartHome.controllable.controllableObjects.ControllableFactoryTest;
import smartHome.controllable.controllableObjects.RadioSocketTest;
import smartHome.services.CommandExecutionTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	RadioSocketTest.class,
	CommandExecutionTest.class,
	ControllableManagerTest.class,
	ControllableFactoryTest.class
})
public class SmartHomeTestSuite {

}
