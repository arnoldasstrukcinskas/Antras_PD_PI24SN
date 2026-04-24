package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "drones")
@XmlAccessorType(XmlAccessType.FIELD)
public class DroneList {

    @XmlElement(name = "drone")
    private List<Drone> drones;

    public List<Drone> getDrones() {
        return drones;
    }

    public void setDrones(List<Drone> drones) {
        this.drones = drones;
    }
}
