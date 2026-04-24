package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_consumer;

import com.example.consumingwebservice.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.math.BigInteger;

public class ManagerClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(ManagerClient.class);

    private String url = "http://localhost:8081/services/managers";

    /**
     * Metodas priregistruojantis vartotoją
     * @param username vartotojo slapyvardis
     * @param password vartotojo slaptažodis
     * @return Gražinamas String pranešimas
     */
    public RegisterResponse register(String username, String password){
        RegisterRequest request = new RegisterRequest();
        request.setUsername(username);
        request.setPassword(password);

        log.info("Registering");

        RegisterResponse response = (RegisterResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);

        return response;
    }

    /**
     * Metodas prijungiantis vartotoją prie sistemos
     * @param username vartotojo slapyvardis
     * @param password vartotojo slaptažodis
     * @return Gražinamas String pranešimas
     */
    public LoginResponse login(String username, String password){
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        log.info("Logging");

        LoginResponse response = (LoginResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);

        return response;
    }

    /**
     * Metodas surandantis droną xml faile pagal drono pavadinimą
     * @param droneName drono pavadinimas
     * @return Grąžinamas drono SOAP objektas
     */
    public GetFromXmlByNameResponse getDroneFromXmlByName(String droneName)
    {
        GetFromXmlByNameRequest request = new GetFromXmlByNameRequest();
        request.setDroneName(droneName);

        log.info("Geting drone by name");

        GetFromXmlByNameResponse response = (GetFromXmlByNameResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);

        return response;
     }

    /**
     * Metodas pridedantis droną prie manageri'io parduotų dronų sąrašo
     * @param droneName drono pavadinimas
     * @return Grąžinamas pridėto drono SOAP objektas
     */
     public SellDroneResponse sellDrone(String droneName)
     {
         SellDroneRequest request = new SellDroneRequest();
         request.setDroneName(droneName);

         log.info("Selling drone");

         SellDroneResponse response = (SellDroneResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);

         return response;
     }

    /**
     * Perkalia dronus iš XML failo į duomenų bazę
     * @return Grąžinamas perkeltų dronų sąrašas
     */
     public MoveDronesFromXmlToDbResponse moveDronesFromXmlToDb()
     {
         MoveDronesFromXmlToDbRequest request = new MoveDronesFromXmlToDbRequest();

         log.info("Moving drones from xml to DB");
         MoveDronesFromXmlToDbResponse response = (MoveDronesFromXmlToDbResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);

         return response;
     }

    /**
     * Sukuria droną duomenų bazėje
     * @param droneName drono pavadinimas
     * @param batteryCapacity drono baterijos dydis
     * @param price drono kaina
     * @param droneNew ar dronas naujas
     * @param frameShape drono rėmo forma
     * @param autonomous ar dronas autonomšikas
     * @return Grąžinamas sukurto drono SOAP objektas
     */
     public CreateDroneInDbResponse createDroneInDb(String droneName, BigInteger batteryCapacity, double price,
                                                    boolean droneNew, char frameShape, boolean autonomous)
     {
        CreateDroneInDbRequest request = new CreateDroneInDbRequest();
        request.setDroneName(droneName);
        request.setBatteryCapacity(batteryCapacity);
        request.setPrice(price);
        request.setDroneNew(droneNew);
        request.setFrameShape(Character.toString(frameShape));
        request.setAutonomous(autonomous);

        log.info("Creating new drone in DB");

        CreateDroneInDbResponse response = (CreateDroneInDbResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);

        return response;
     }

    /**
     * Atnaujina drono duomenis duomenų bazėje
     * @param droneName drono pavadinimas
     * @param newDroneName naujas drono pavadinimas
     * @param price drono kaina
     * @param batteryCapacity baterijos talpa
     * @return Grąžinamas atnaujinto drono SOAP objektas
     */
     public UpdateDroneInDbResponse updateDroneInDb(String droneName, String newDroneName,
                                                    double price, BigInteger batteryCapacity)
     {
        UpdateDroneInDbRequest request = new UpdateDroneInDbRequest();
        request.setDroneName(droneName);
        request.setNewName(newDroneName);
        request.setPrice(price);
        request.setBatteryCapacity(batteryCapacity);

        log.info("Updating drone info");

        UpdateDroneInDbResponse response = (UpdateDroneInDbResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);

        return response;
     }

    /**
     * Pašalina droną iš duomenų bazės
     * @param droneName drono pavadinimas
     * @return Grąžinamas pašalinto drono pavadinimas
     */
     public DeleteDroneFromDbResponse deleteDroneFromDb(String droneName)
     {
         DeleteDroneFromDbRequest request = new DeleteDroneFromDbRequest();
         request.setDroneName(droneName);

         log.info("Deleting drone from DB");

         DeleteDroneFromDbResponse response = (DeleteDroneFromDbResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);

         return response;
     }

    /**
     * Metodas grąžinantis visą manager'ių sąrašą iš duomenų bazės
     * @return Grąžinamas manager'ių sąrašas
     */
    public GetManagersResponse getManagers(){

        GetManagersRequest request = new GetManagersRequest();

        log.info("Getting managers");

        GetManagersResponse response = (GetManagersResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);

        return response;
    }

    /**
     * Metodas sukuriantis PDF failą iš XML faile esančių duomenų.
     * @return Grąžinamas pranešimas sėkmės atveju
     */
    public CreatePdfFileResponse createPdf(){
        CreatePdfFileRequest request = new CreatePdfFileRequest();

        log.info("Creating");

        CreatePdfFileResponse response = (CreatePdfFileResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);

        return response;
    }


    public void printAllManagers(){
        GetManagersResponse response = getManagers();

        log.info("=== PRADEDAMAS DUOMENŲ SPAUSDINIMAS ===");

        if (response.getManager().isEmpty()) {
            log.warn("Serveris negrąžino jokių vadybininkų.");
            return;
        }

        response.getManager().forEach(m -> {
            log.info("-------------------------------------------");
            log.info("VADYBININKAS: {} (ID: {})", m.getUsername(), m.getId());
            log.info("SĄSKAITA: {}", m.getAccountNumber());

            if (m.getDrone().isEmpty()) {
                log.info("  [Dronų neturi]");
            } else {
                log.info("  TURIMI DRONAI ({} vnt.):", m.getDrone().size());
                m.getDrone().forEach(d ->
                        log.info("    - {} | Kaina: {} | Baterija: {} mAh",
                                d.getDroneName(), d.getPrice(), d.getBatteryCapacity())
                );
            }
        });

        log.info("=== SPAUSDINIMAS BAIGTAS ===");
    }
}
