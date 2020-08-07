package ui.utils;

public class TempratureData {

    String temp;
    String humidity;

    public TempratureData(String temp,String humidity)
    {
        this.temp = temp;
        this.humidity = humidity;
    }

    public String getTemp()
    {
        return this.temp;
    }

    public String getHumidity()
    {
        return this.humidity;
    }


}
