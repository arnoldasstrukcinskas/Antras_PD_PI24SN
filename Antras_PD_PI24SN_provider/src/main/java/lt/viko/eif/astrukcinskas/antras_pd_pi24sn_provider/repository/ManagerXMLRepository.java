package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.repository;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Drone;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.DroneList;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Manager;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.ManagerList;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class ManagerXMLRepository {
    private String filePath = "src/main/resources/data/managers.xml";

    /**
     * Gauna visus manager'ius iš xml failo
     * @return Grąžina visų managerių sąrašą
     */
    public ManagerList getAllManagers() {
        try {
            System.out.println("repo started");
            System.setProperty("javax.xml.accessExternalDTD", "all");

            File file = new File(filePath);

            JAXBContext context = JAXBContext.newInstance(ManagerList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            //Comment if u will use docker
            ManagerList managerList = (ManagerList) unmarshaller.unmarshal(file);

            return managerList;
        } catch (Exception e) {
            System.out.printf("Server repo: %s%n", e);
            return null;
        }
    }

    /**
     * Metodas, kuris surašo visą managerių sąrašą į xml failą
     * @param managers managerių sąrašas, kuris įrašomas į xml failą
     * @return Grąžina žinutę ar manageriai įrašyti ar ne.
     */
    public String writeAllManagersToFile(List<Manager> managers)  {
        try {
            System.setProperty("javax.xml.accessExternalDTD", "all");
            File file = new File(filePath);

            JAXBContext context = JAXBContext.newInstance(ManagerList.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "managers.xsd");

            ManagerList managerList = new ManagerList();
            managerList.setManagers(managers);

            marshaller.marshal(managerList, file);

            return "Repo success";

        } catch (Exception e) {
            System.out.printf("Server repo: failed to write to file %s%n", e);
            return "Server repo: Failed to add manager";
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
