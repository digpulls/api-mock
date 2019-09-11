package extentions;

import org.testng.IClassListener;
import org.testng.IMethodInstance;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;

public class TestCallback implements ISuiteListener, IClassListener {


    @Override
    public void onBeforeClass(ITestClass testClass, IMethodInstance mi) {

    }

    @Override
    public void onAfterClass(ITestClass testClass, IMethodInstance mi) {

    }

    @Override
    public void onStart(ISuite suite) {

    }

    @Override
    public void onFinish(ISuite suite) {

    }


}

