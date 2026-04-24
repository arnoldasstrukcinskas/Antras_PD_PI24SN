package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Table(name = "managers")
@Entity
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"id", "accountNumber", "username", "password", "soldDrones"})
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "account_number")
    private BigInteger accountNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "manager_drones", // Tarpinės lentelės pavadinimas
            joinColumns = @JoinColumn(name = "manager_id"), // FK į Manager lentelę
            inverseJoinColumns = @JoinColumn(name = "drone_id") // FK į Drone lentelę
    )
       private List<Drone> soldDrones = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigInteger getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(BigInteger accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElementWrapper(name = "soldDrones")
    @XmlElement(name = "drone")
    public List<Drone> getSoldDrones() {
        return soldDrones;
    }

    public void setSoldDrones(List<Drone> soldDrones) {
        this.soldDrones = soldDrones;
    }
}
