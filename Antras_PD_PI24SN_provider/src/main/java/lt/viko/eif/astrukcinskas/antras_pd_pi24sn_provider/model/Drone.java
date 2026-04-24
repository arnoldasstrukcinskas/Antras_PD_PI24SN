package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.adapter.CharAdapter;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.adapter.LocalDateTimeAdapter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "drones")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @XmlTransient
    private int id;

    @Column(name = "drone_name")
    private String droneName;

    @Column(name = "battery_capacity")
    private BigInteger batteryCapacity;

    @Column(name = "price")
    private double price;

    @Column(name = "drone_new")
    private boolean droneNew;

    @XmlJavaTypeAdapter(CharAdapter.class)
    @Column(name = "FrameShape")
    private Character framsShape;

    @Column(name = "is_autonomus")
    private boolean isAutonomus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDroneNew() {
        return droneNew;
    }

    public void setDroneNew(boolean droneNew) {
        this.droneNew = droneNew;
    }

    public Character getFramsShape() {
        return framsShape;
    }

    public void setFramsShape(Character framsShape) {
        this.framsShape = framsShape;
    }

    public boolean isAutonomus() {
        return isAutonomus;
    }

    public void setAutonomus(boolean autonomus) {
        isAutonomus = autonomus;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", droneName='" + droneName + '\'' +
                ", batteryCapacity=" + batteryCapacity +
                ", price=" + price +
                ", droneNew=" + droneNew +
                ", framsShape=" + framsShape +
                ", isAutonomus=" + isAutonomus +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Drone drone = (Drone) o;
        return Double.compare(getPrice(), drone.getPrice()) == 0 && isDroneNew() == drone.isDroneNew() && isAutonomus() == drone.isAutonomus() && Objects.equals(getDroneName(), drone.getDroneName()) && Objects.equals(getBatteryCapacity(), drone.getBatteryCapacity()) && Objects.equals(getFramsShape(), drone.getFramsShape());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getDroneName());
        result = 31 * result + Objects.hashCode(getBatteryCapacity());
        result = 31 * result + Double.hashCode(getPrice());
        result = 31 * result + Boolean.hashCode(isDroneNew());
        result = 31 * result + Objects.hashCode(getFramsShape());
        result = 31 * result + Boolean.hashCode(isAutonomus());
        return result;
    }
}
