package com.test.simpleweatherapi.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class WeatherInfo {

    @Getter @Setter
    private double temp;

    @Getter @Setter
    private double maxTemp;

    @Getter @Setter
    private double minTemp;

    @Getter @Setter
    private int pressure;

    @Getter @Setter
    private int humidity;

    @Getter @Setter
    private String weather;

    @Getter @Setter
    private String weatherDescription;

    @Getter @Setter
    private String icon;

    @Getter @Setter
    private Date date;
}
