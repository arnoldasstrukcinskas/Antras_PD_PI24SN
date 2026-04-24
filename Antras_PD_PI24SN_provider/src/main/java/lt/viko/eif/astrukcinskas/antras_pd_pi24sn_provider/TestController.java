package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider;

import jakarta.security.auth.message.AuthException;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.Service.DroneDTO;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.Service.DroneService;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.Service.ManagerService;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Drone;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Manager;
import org.apache.fop.apps.FOPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.OperationsException;
import javax.security.auth.login.LoginException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class TestController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private DroneService droneService;


    /**
     * Metodas vykdantis vartotojo registraciją sistemoje
     * @param username Vartotojo objektas
     * @param password Vartotojo slaptazodis
     * @returns Grąžinama statuso žinutė
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam(name = "username") String username,
                                           @RequestParam(name = "password") String password) {

        if(username.isEmpty() || password.isEmpty())
        {
            throw new IllegalArgumentException("Test controller: add username or password");
        }
        String response = managerService.register(username, password);
        return ResponseEntity.ok(response);
    }

    /**
     * Metodas vykdantis vartotojo prijungimą prie sistemos
     * @param username Vartotojo slapyvardis
     * @param password Vartotojo slaptažodis
     * @return Grąžinama statuso žinutė
     * @throws LoginException
     */
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam(name = "username") String username,
                                        @RequestParam(name = "password") String password) throws LoginException {
        String response = "";
        try {
            response = managerService.login(username, password);
        } catch (AuthException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Metodas vykdantis drono sukūrimą duomenų bazėje
     * @param droneDTO Duomenų perdavimo objektas
     * @return Grąžinama statuso žinutė
     */
    @PostMapping("/createDroneInDb")
    public ResponseEntity<String> createDroneInDb(@RequestBody DroneDTO droneDTO) throws AuthException {
        Drone response;
        try {
            response = managerService.createDroneInDb(droneDTO);
        } catch (UnsupportedOperationException e)
        {
            return ResponseEntity.badRequest().body("Manager Controller, failed to creade" + e.getMessage());
        }

        return ResponseEntity.ok(response.toString());

    }

    /**
     * Metodas atnaujinantis drono informaciją duomenų bazėje
     * @param droneName Drono pavadinimas
     * @param droneDTO Drono duomenų perdavimo objektas
     * @returnGrąžinama statuso žinutė
     */
    @PutMapping("/updateDroneInDb")
    public ResponseEntity<String> updateDroneInDb(@RequestParam String droneName,
                                                  @RequestBody DroneDTO droneDTO) throws AuthException {
        String response = "";

        try {
            response = managerService.updateDroneInDb(droneName, droneDTO).toString();
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body("Manager controller: Failed to update drone." + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Metodas vykdantis drono pašalinimą iš duomenų bazės
     * @param droneName Drono pavadinimas
     * @return Grąžinama statuso žinutė
     * @throws AuthException
     */
    @DeleteMapping("/deleteDroneInDb")
    public ResponseEntity<String> deleteDroneInDb(@RequestParam String droneName) throws AuthException {
        String response = "";

        try {
            response = managerService.deleteDroneFromDb(droneName).toString();
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body("Manager controller: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Metodas vykdantis visų dronų grąžinimą iš duomenų bazės
     * @return Grąžinama statuso žinutė ir dronų sąrašas
     * @throws AuthException
     */
    @GetMapping("/getAllFromDB")
    public ResponseEntity<List<Drone>> getAllFromDB() throws AuthException {
        List<Drone> drones = new ArrayList<>();

        try {
            drones = managerService.getAllDronesFromDB();
        } catch (UnsupportedOperationException e){
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(drones);

    }

    /**
     * Metodas vykdantis visų dronų suradimą pagal pavadinimą duomenų bazėje
     * @param droneName Drono pavadinimas
     * @return Grąžinama statuso žinutė ir drono objektas
     * @throws AuthException
     */
    @GetMapping("/getDroneFromDbByName")
    public ResponseEntity<Drone> getDroneFromDbByName(@RequestParam String droneName) throws AuthException {
        Drone response = new Drone();

        try {
            response = managerService.getFromDbByName(droneName);
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Metodas vykdantis visų dronų perkėlimą iš XMl failo į duomenų bazę
     * @return Grąžinama statuso žinutė
     * @throws AuthException
     * @throws IOException
     */
    @PostMapping("/moveFromXmlToDb")
    public ResponseEntity<String> moveFromXmlToDb() throws AuthException {
        String response = "";

        try {
            managerService.moveDronesFromXmlToDB();
        } catch (UnsupportedOperationException | OperationsException e){
            return ResponseEntity.badRequest().body("Manager controller: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


//    XML FILE METHODS
//    -------------------------------------------------------------------------

       /**
     * Metodas vykdantis drono duomenų atnaujinimą XML faile
     * @param droneName Drono pavadinimas
     * @param droneDTO Drono duomenų perdavimo objektas
     * @return Grąžinama statuso žinutė
     * @throws AuthException
     */
    @PutMapping("/updateDroneInXml")
    public ResponseEntity<String> UpdateDroneInXml(@RequestParam String droneName,
                                                   @RequestBody DroneDTO droneDTO) throws AuthException {
        String response = "";

        try {
            response = managerService.updateDroneInXml(droneName ,droneDTO).toString();
        } catch (UnsupportedOperationException | InvalidObjectException | OperationsException e) {
            return ResponseEntity.badRequest().body("Manager controller: failed to update drone in xml" + e.getMessage());
        }

        return ResponseEntity.ok(response);

    }

    /**
     * Metodas vykdantis dronų paeišką pagal pavadinimą XML faile
     * @param droneName Drono pavadinimas
     * @return Grąžinama statuso žinutė ir drono objektas
     */
    @GetMapping("/getFromXmlByName")
    public ResponseEntity<Drone> getDroneFromXmlByName(@RequestParam String droneName){
        Drone response;

        try {
            response = managerService.getDroneFromXmlByName(droneName);
        } catch (IOException | AuthException e){
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllManagersXML")
    public ResponseEntity<List<Manager>> getAllManagersFromXml() throws AuthException {
        List<Manager> managers;

        managers = managerService.getManagersFromXml();

        return ResponseEntity.ok(managers);
    }

    @PostMapping("/sellDrone")
    public ResponseEntity<Drone> sellDrone(@RequestParam String droneName) throws AuthException {

        Drone response;

        response = managerService.addDroneToSoldList(droneName);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/generatePDF")
    public ResponseEntity<String> generatePDF() throws FOPException, IOException, TransformerException {
        String response = managerService.convertToPdf();
        return ResponseEntity.ok(response);
    }

//    <?xml-stylesheet type="text/xsl" href="managers.xsl"?>
}
