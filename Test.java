package ch.zhaw.mas8i.pendlersupport;

import java.util.logging.Level;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ch.zhaw.mas8i.pendlersupport.model.*;
import ch.zhaw.mas8i.pendlersupport.controller.*;

@RunWith(Suite.class)
@SuiteClasses({
	ConfigurationSerializedFileTest.class,
	ConnectionTrackerTest.class,
	LocationTest.class,
	ConfigurationTest.class,
	ConnectionTest.class,
	OpenDataConnectionTest.class,
	OpenDataLocationTest.class,
	TimeTest.class,
	OpenDataTest.class
})

public class Test {
	@BeforeClass
    public static void setLogLevel() {
		Log.setLevel(Level.OFF);
    }
}
