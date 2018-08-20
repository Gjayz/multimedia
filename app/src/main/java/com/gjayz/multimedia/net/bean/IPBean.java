package com.gjayz.multimedia.net.bean;

import com.gjayz.multimedia.net.base.BaseBean;

public class IPBean extends BaseBean {

    /**
     * ip : 183.128.153.78
     * ip_decimal : 3078658382
     * country : China
     * country_iso : CN
     * city : Hangzhou
     */

    private String ip;
    private long ip_decimal;
    private String country;
    private String country_iso;
    private String city;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getIp_decimal() {
        return ip_decimal;
    }

    public void setIp_decimal(long ip_decimal) {
        this.ip_decimal = ip_decimal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_iso() {
        return country_iso;
    }

    public void setCountry_iso(String country_iso) {
        this.country_iso = country_iso;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "IPBean{" +
                "ip='" + ip + '\'' +
                ", ip_decimal=" + ip_decimal +
                ", country='" + country + '\'' +
                ", country_iso='" + country_iso + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}