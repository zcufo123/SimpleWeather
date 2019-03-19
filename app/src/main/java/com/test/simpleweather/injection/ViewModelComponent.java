package com.test.simpleweather.injection;

import com.test.simpleweather.MainActivity;
import com.test.simpleweather.ui.WeatherFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ViewModelModule.class})
public interface ViewModelComponent {
    void inject(MainActivity mainActivity);
    void inject(WeatherFragment fragment);
}
