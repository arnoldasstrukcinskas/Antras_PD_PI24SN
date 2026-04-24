package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "managers")
@XmlAccessorType(XmlAccessType.FIELD)
public class ManagerList {

    @XmlElement(name = "manager")
    private List<Manager> managers;

    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }
}