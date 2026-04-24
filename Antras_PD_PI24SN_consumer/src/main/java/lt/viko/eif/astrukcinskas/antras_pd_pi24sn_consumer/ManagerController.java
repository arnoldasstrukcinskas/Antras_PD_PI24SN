package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_consumer;

import com.example.consumingwebservice.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
public class ManagerController {

    @Autowired
    private ManagerClient managerCLient;

    @GetMapping("/login")
    public LoginResponse login(@RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password){
        return managerCLient.login(username, password);
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestParam(name = "username") String username,
                                     @RequestParam(name = "password") String password){
        return managerCLient.register(username, password);
    }

    @GetMapping("/GetDroneFromXmlByName")
    public GetFromXmlByNameResponse getDroneFromXmlByName(@RequestParam(name = "droneName") String droneName){
        return managerCLient.getDroneFromXmlByName(droneName);
    }

    @PostMapping("/sell")
    public SellDroneResponse sellDrone(@RequestParam(name = "droneName") String droneName){
        return managerCLient.sellDrone(droneName);
    }

    @PostMapping("/MoveDroneFromXmlToDb")
    public MoveDronesFromXmlToDbResponse moveDronesFromXmlToDb(){
        return managerCLient.moveDronesFromXmlToDb();
    }

    @PostMapping("/CreateInDb")
    public CreateDroneInDbResponse createDroneInDb(@RequestParam(name = "droneName") String droneName,
                                                   @RequestParam(name = "batteryCapacity") BigInteger batteryCapacity,
                                                   @RequestParam(name = "price") double price,
                                                   @RequestParam(name = "droneNew") boolean droneNew,
                                                   @RequestParam(name = "frameShape") char frameShape,
                                                   @RequestParam(name = "isAutonomous") boolean isautonomous){
        return managerCLient.createDroneInDb(droneName, batteryCapacity, price, droneNew, frameShape, isautonomous);
    }

    @PutMapping("/UpdateDroneInDb")
    public UpdateDroneInDbResponse updateDroneInDb(@RequestParam(name = "droneName") String droneName,
                                                   @RequestParam(name = "newDroneName") String newDroneName,
                                                   @RequestParam(name = "batteryCapacity") BigInteger batteryCapacity,
                                                   @RequestParam(name = "price") double price){
        return managerCLient.updateDroneInDb(droneName, newDroneName, price, batteryCapacity);
    }

    @DeleteMapping("/DeleteDroneFromDb")
    public DeleteDroneFromDbResponse deleteDroneFromDb(@RequestParam(name = "droneName") String droneName){
        return managerCLient.deleteDroneFromDb(droneName);
    }

    @GetMapping("/getAllManagers")
    public GetManagersResponse getManagers(){
        return managerCLient.getManagers();
    }

    @GetMapping("/pdf")
    public CreatePdfFileResponse CreatePDF(){
        return managerCLient.createPdf();
    }

}
