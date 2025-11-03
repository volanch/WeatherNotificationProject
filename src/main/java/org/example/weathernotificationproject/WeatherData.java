package org.example.weathernotificationproject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeatherData {
    private String city;
    private String date;
    private double temperature;
    private double apparentTemperature;
    private double humidity;
    private double windSpeed;
    private double precipitation;
    private double rain;
    private String weatherCode;

    private WeatherData(Builder builder){
        this.city = builder.city;
        this.date = builder.date;
        this.temperature = builder.temperature;
        this.apparentTemperature = builder.apparentTemperature;
        this.humidity = builder.humidity;
        this.windSpeed = builder.windSpeed;
        this.precipitation = builder.precipitation;
        this.rain = builder.rain;
        this.weatherCode = builder.weatherCode;
    }

    public static class Builder{
        private String city;
        private String date;
        private double temperature;
        private double apparentTemperature;
        private double humidity;
        private double windSpeed;
        private double precipitation;
        private double rain;
        private String weatherCode;


        public Builder city(String city){
            this.city = city;
            return this;
        }
        public Builder date(String date){
            this.date = date;
            return this;
        }
        public Builder temperature(double temperature){
            this.temperature = temperature;
            return this;
        }
        public Builder apparentTemperature(double apparentTemperature){
            this.apparentTemperature = apparentTemperature;
            return this;
        }
        public Builder humidity(double humidity){
            this.humidity = humidity;
            return this;
        }
        public Builder windSpeed(double windSpeed){
            this.windSpeed = windSpeed;
            return this;
        }
        public Builder precipitation(double precipitation){
            this.precipitation = precipitation;
            return this;
        }
        public Builder rain(double rain){
            this.rain = rain;
            return this;
        }
        public Builder weatherCode(String weatherCode){
            this.weatherCode = weatherCode;
            return this;
        }
        public WeatherData build(){
            return new WeatherData(this);
        }
    }
}
