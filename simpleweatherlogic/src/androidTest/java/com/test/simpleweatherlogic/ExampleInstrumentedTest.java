package com.test.simpleweatherlogic;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.test.simpleweatherlogic.test", appContext.getPackageName());

//        LifecycleOwner lifecycleOwner = Mockito.mock(LifecycleOwner.class);
//        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(Mockito.mock(LifecycleOwner.class));
//        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }
}
