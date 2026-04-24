package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.Service;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class DroneDTO {

    private String droneName;
    private BigInteger batteryCapacity;
    private double price;
    private boolean droneNew;
    private char framsShape;
    private boolean isAutonomus;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDroneName() {
        return droneName;
    }

    public void setDroneName(String droneName) {
        this.droneName = droneName;
    }

    public BigInteger getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(BigInteger batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public boolean getDroneNew() {
        return droneNew;
    }

    public void setDroneNew(boolean droneNew) {
        this.droneNew = droneNew;
    }

    public char getFramsShape() {
        return framsShape;
    }

    public void setFramsShape(char framsShape) {
        this.framsShape = framsShape;
    }

    public boolean isAutonomus() {
        return isAutonomus;
    }

    public void setAutonomus(boolean autonomus) {
        isAutonomus = autonomus;
    }
}
