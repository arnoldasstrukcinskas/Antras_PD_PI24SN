package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.Service;

import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Drone;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Manager;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.repository.Interfaces.ManagerRepository;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.repository.ManagerXMLRepository;
import org.apache.fop.apps.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.OperationsException;
import javax.security.auth.login.LoginException;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private DroneService droneService;

    @Autowired
    private ManagerXMLRepository managerXMLRepository;

    private Manager loggedManager;

    /**
     * Metodas užregistruojantis vartotoją duomenų bazėje
     * @param username Vartotojo slapyvardis
     * @param password Vartotojo slaptazodis
     * @return Grąžinamas užregistruoto vartotojo slapyvardį
     */
    public String register(String username, String password) {
        if(username.isEmpty() || password.isEmpty())
        {
            throw new IllegalArgumentException("Test controller: add username or password");
        }
        Manager manager = new Manager();

        BigInteger randomNumber = new BigInteger(64, new Random());
        manager.setUsername(username);
        manager.setPassword(password);
        manager.setAccountNumber(randomNumber);

        Manager response = managerRepository.save(manager);

//        Write to XML at the same time
        List<Manager> managers = managerXMLRepository.getAllManagers().getManagers();
        if (managers == null)
        {
            managers = new ArrayList<>();
        }

        manager.setPassword("");
        managers.add(manager);

        managerXMLRepository.writeAllManagersToFile(managers);


        return response.getUsername();
    }

    /**
     * Metodas, prijungiantis vartotoją prie sistemos
     * @param username Vartotojo slapyvardis
     * @param password Vartotojo slaptažodis
     * @return Grąžinamas prijungto vartotojo slaptažodis
     * @throws LoginException
     */
    public String login(String username, String password) throws LoginException {
        Manager manager = managerRepository.findFirstByUsername(username);

        if (password.equals(manager.getPassword())){
            loggedManager = manager;
            return "Logged in: " + manager.getUsername();
        } else {
            throw new LoginException("Failed to login");
        }
    }

    /**
     * Metodas kontaktuojantis su DronoServcie, kuriantis naują droną duomenų bazėje
     * @param droneDTO Drono duomenų perdavimo objektas
     * @return Grąžinamas tekstas apie operacijos būseną
     * @throws AuthException
     */
    public Drone createDroneInDb(DroneDTO droneDTO) throws AuthException {
        if (loggedManager == null)
        {
            throw new AuthException("Manager controller: Need to log in");
        }
        return droneService.createDroneInDb(droneDTO);
    }

    /**
     * Metodas kontaktuojantis su DronoService, atnaujinantis drono informaciją duomenų bazėje
     * @param droneName Drono pavadinimas
     * @param droneDTO Drono duomenų perdavimo objektas
     * @return Grąžinamas tekstas apie operacijos būseną
     * @throws AuthException
     */
    public Drone updateDroneInDb(String droneName, DroneDTO droneDTO) throws AuthException {
        if (loggedManager == null)
        {
            throw new AuthException("Manager controller: Need to log in");
        }
        return droneService.updateDroneInDb(droneName, droneDTO);
    }

    /**
     * Metodas kontaktuojantis su DronoService, pašalinantis droną iš duomenų bazės
     * @param droneName Drono pavadinimas
     * @return Grąžinamas tekstas apie operacijos būseną
     * @throws AuthException
     */
    public Drone deleteDroneFromDb(String droneName) throws AuthException {
        if (loggedManager == null)
        {
            throw new AuthException("Manager controller: Need to log in");
        }
        return droneService.deleteDroneFromDb(droneName);
    }

    /**
     * Metodas kontaktuojantis su DronoService, surandantis droną duomenų bazėje pagal pavadinimą
     * @param droneName Drono pavadinimas
     * @return Grąžinamas drono objektas
     * @throws AuthException
     */
    public Drone getFromDbByName(String droneName) throws AuthException {
        if (loggedManager == null)
        {
            throw new AuthException("Manager controller: Need to log in");
        }
        return droneService.getDroneFromDbByName(droneName);
    }

    /**
     * Metodas kontaktuojantis su DronoService, gaunantis visus dronus iš duomenų bazės
     * @return Grąžinamas tekstas apie operacijos būseną
     * @throws AuthException
     */
    public List<Drone> getAllDronesFromDB() throws AuthException {
        if (loggedManager == null)
        {
            throw new AuthException("Manager controller: Need to log in");
        }
        return droneService.getAllDronesFromDB();
    }

    /**
     * Metodas pridedantis parduodą droną prie vadybininko parduotų dronų sąrašo.
     * @param droneName parduodo drono pavadiniams
     * @return Parduoto drono objektas
     * @throws AuthException
     */
    @Transactional
    public Drone addDroneToSoldList(String droneName) throws AuthException {
        if (loggedManager == null)
        {
            throw new AuthException("Manager controller: Need to log in");
        }

        Drone drone = droneService.getDroneFromXmlByName(droneName);
        Manager manager = findManagerByUserName(loggedManager.getUsername());

        List<Drone> soldDrones = manager.getSoldDrones();
        soldDrones.add(drone);

        var response = updateManagerInXml(manager);
        managerRepository.save(manager);
        loggedManager = manager;

        return drone;
    }

//    XML FILE METHODS
//    --------------------------------------------------------------------

    /**
     * Metodas kontaktuojantis su DronoService, atnaujinantis norimo drono
     * informaciją XML faile
     * @param droneName Drono pavadinimas
     * @param droneDTO Drono duomenų perdavimo objetkas
     * @return Grąžinamas atnaujinto drono objektas
     * @throws InvalidObjectException
     * @throws OperationsException
     * @throws AuthException
     */
    public Drone updateDroneInXml(String droneName, DroneDTO droneDTO) throws InvalidObjectException, OperationsException, AuthException {
        if (loggedManager == null)
        {
            throw new AuthException("Manager controller: Need to log in");
        }

        var response = droneService.updateDroneInXml(droneName, droneDTO);
        if (response == null)
        {
            throw new InvalidObjectException("Manager service: failed to update drone in XML");
        }

        return response;
    }

    /**
     * Metodas surandantis drono objektą XML faile pagal pavadinimą
     * @param droneName Tikslus drono pavadinimas
     * @return Grąžinamas rasto drono objektas
     * @throws InvalidObjectException
     * @throws AuthException
     */
    public Drone getDroneFromXmlByName(String droneName) throws InvalidObjectException, AuthException {
        if (loggedManager == null)
        {
            throw new AuthException("Manager controller: Need to log in");
        }

        var response = droneService.getDroneFromXmlByName(droneName);
        if(response == null)
        {
            throw new InvalidObjectException("Manager service: failed to get drone from xml by name");
        }
        return response;
    }

    /**
     * Metodas kontaktuojantis su DronoService, perkeliantis visus dronų duomenis
     * iš XML failo į duomenų bazę
     * @return Grąžinamas tekstas apie operacijos būseną
     * @throws OperationsException
     * @throws AuthException
     */
    public List<Drone> moveDronesFromXmlToDB() throws OperationsException, AuthException {
        if (loggedManager == null)
        {
            throw new AuthException("Manager controller: Need to log in");
        }

        var response = droneService.moveDronesFromXmlToDB();
        return response;
    }

    /**
     * Metodas grąžinantis visus vadybininkus esančius XML faile
     * @return Vadybininkų sąrašą
     * @throws AuthException
     */
    public List<Manager> getManagersFromXml() throws AuthException {
        if (loggedManager == null)
        {
            throw new AuthException("Manager controller: Need to log in");
        }
        return managerXMLRepository.getAllManagers().getManagers();
    }

    /**
     * Metodas atnaujinantis drono duomenis XML faile
     *
     * @return Tekstinį pranešimą: sėkmės atveju grąžinama patvirtinimo žinutė iš repozitorijos,
     * o jei dronas jau egzistuoja arba įvyksta klaida – pranešimas apie nesėkmę.
     * @throws AuthException
     */
    public Manager updateManagerInXml(Manager manager) throws AuthException {
        if (loggedManager == null)
        {
            throw new AuthException("Manager controller: Need to log in");
        }

        List<Manager> managers = managerXMLRepository.getAllManagers().getManagers();
        int managerIndex = 0;
        manager.setPassword("");

        for (Manager managerCheck : managers) {
            if (managerCheck.getUsername().equals(manager.getUsername())) {
                managers.set(managerIndex, manager);
                managerXMLRepository.writeAllManagersToFile(managers);
                return manager;
            }
            managerIndex++;
        }
        return null;
    }

//    Convert to HTML and PDF methods

    /**
     * Metodas sukuriantis pdf failą iš XML failo duomenų
     * @return Grąžinama sėkmės žinutė
     * @throws IOException
     * @throws FOPException
     * @throws TransformerException
     */
    public String convertToPdf() throws IOException, FOPException, TransformerException {
        File xsltFile = new File("managers-to-pdf.xsl");
        StreamSource xmlSource = new StreamSource(new File("managers.xml"));
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out;
        out = new java.io.FileOutputStream("managers1.pdf");

        try{
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            Result result = new SAXResult(fop.getDefaultHandler());
            transformer.transform(xmlSource, result);
        } finally {
            out.close();
        }

        return "Success";
    }

//    Helpers
//    Metodas kontroleriui

    /**
     * Metodas surandantis vartotoją pagal slapyvardį
     * @param username Vartotojo slapyvardis
     * @return Grąžinamas vartotojo objektas
     */
    public Manager findManagerByUserName(String username) {
        Manager manager = managerRepository.findFirstByUsername(username);

        if (manager == null) {
            return null;
        }
        return manager;
    }

}
