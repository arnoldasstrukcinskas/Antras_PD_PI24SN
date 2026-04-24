package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.repository;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Drone;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.DroneList;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class DronesXMLRepository {

    private String filePath = "src/main/resources/data/drones.xml";

    /**
     * Gauna visus dronus iš xml failo
     * @return Grąžina visų dronų sąrašą
     */
    public DroneList getAllDrones() {
        try {
            System.out.println("repo started");
            System.setProperty("javax.xml.accessExternalDTD", "all");

            File file = new File(filePath);

            JAXBContext context = JAXBContext.newInstance(DroneList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            //Comment if u will use docker
            DroneList droneList = (DroneList) unmarshaller.unmarshal(file);

            return droneList;
        } catch (Exception e) {
            System.out.printf("Server repo: %s%n", e);
            return null;
        }
    }

    /**
     * Metodas, kuris surašo visą dronų sąrašą į xml failą
     * @param drones dronų sąrašas, kuris įrašomas į xml failą
     * @return Grąžina žinutę ar droonai įrašyti ar ne.
     */
    public String writeAllDronesToFile(List<Drone> drones)  {
        try {
            System.setProperty("javax.xml.accessExternalDTD", "all");
            File file = new File(filePath);

            JAXBContext context = JAXBContext.newInstance(DroneList.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "drones.xsd");

            DroneList droneList = new DroneList();
            droneList.setDrones(drones);

            marshaller.marshal(droneList, file);

            return "Repo success";

        } catch (Exception e) {
            System.out.printf("Server repo: failed to write to file %s%n", e);
            return "Repo failed";
        }
    }

    /**
     * Pagalbinis metodas testavimui, kad testams būtų galiam naudoti atskirą failą.
     * @param filePath Failo direktoriją, kurioje yra xml failas.
     */
    // Metodas, leidžiantis testui pakeisti kelią
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
