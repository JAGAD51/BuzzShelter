package com.buzzshelter.Model;

/**
 * Created by jeffr on 2/20/2018.
 */

public class Shelter {
    private String name;
    private String capacity;
    private String restrictions;
    private String longitude;
    private String latitude;
    private String address;
    private String phoneNumber;
    private int vacancy;

    public Shelter(String name, String capacity, String restrictions, String longitude,
                   String latitude, String address, String phoneNumber) {
        this.name = name;
        this.capacity = capacity;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.vacancy = 0;
        if (!capacity.equals("")) {
            String[] capacities = capacity.split(",");
            for (int i = 0; i < capacities.length; i++) {
                String numVacancy = capacities[i].replaceAll("\\D+","");
                this.vacancy += Integer.parseInt(numVacancy);
            }
        }
    }
    public String toString() {
        return name + " - " + restrictions;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getVacancy() {
        return this.vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Shelter)) {
            return false;
        }
        return this.getName().equals(((Shelter) object).getName());
    }
}
