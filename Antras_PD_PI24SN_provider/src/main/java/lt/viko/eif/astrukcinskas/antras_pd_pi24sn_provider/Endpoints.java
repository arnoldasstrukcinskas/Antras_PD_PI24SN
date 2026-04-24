package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider;

import io.spring.guides.gs_producing_web_service.*;
import jakarta.security.auth.message.AuthException;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.Service.DroneDTO;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.Service.ManagerService;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Drone;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Manager;
import org.apache.fop.apps.FOPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.management.OperationsException;
import javax.security.auth.login.LoginException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

@Endpoint
public class Endpoints {
    private static final String url = "https://spring.io/guides/gs-producing-web-service";

    @Autowired
    private ManagerService managerService;

    @PayloadRoot(namespace = url, localPart = "getManagersRequest")
    @ResponsePayload
    public GetManagersResponse getManagers(@RequestPayload GetManagersRequest request) throws AuthException {
        GetManagersResponse response = new GetManagersResponse();

        List<ManagerSOAP> managers = toSoap(managerService.getManagersFromXml());
        response.getManager().addAll(managers);

        return response;
    }

    @PayloadRoot(namespace = url, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest request) throws LoginException {
        LoginResponse response = new LoginResponse();

        String message = managerService.login(request.getUsername(), request.getPassword());
        response.setMessage(message);

        return response;
    }

    @PayloadRoot(namespace = url, localPart = "registerRequest")
    @ResponsePayload
    public RegisterResponse register(@RequestPayload RegisterRequest request) throws LoginException {
        RegisterResponse response = new RegisterResponse();

        String message = managerService.register(request.getUsername(), request.getPassword());
        response.setMessage(message);

        return response;
    }

    @PayloadRoot(namespace = url, localPart = "getFromXmlByNameRequest")
    @ResponsePayload
    public GetFromXmlByNameResponse getFromXmlByName(@RequestPayload GetFromXmlByNameRequest request) throws AuthException, InvalidObjectException {
        GetFromXmlByNameResponse repsonse = new GetFromXmlByNameResponse();

        Drone drone = managerService.getDroneFromXmlByName(request.getDroneName());
        DroneSOAP droneSOAP = convertDroneFromEneotyToSOAP(drone);

        repsonse.setDone(droneSOAP);

        return repsonse;
    }

    @PayloadRoot(namespace = url, localPart = "sellDroneRequest")
    @ResponsePayload
    public SellDroneResponse sellDrone(@RequestPayload SellDroneRequest request) throws AuthException {
        SellDroneResponse response= new SellDroneResponse();

        Drone drone = managerService.addDroneToSoldList(request.getDroneName());

        DroneSOAP droneSOAP = convertDroneFromEneotyToSOAP(drone);

        response.setDrone(droneSOAP);

        return response;
    }

    @PayloadRoot(namespace = url, localPart = "moveDronesFromXmlToDbRequest")
    @ResponsePayload
    public MoveDronesFromXmlToDbResponse moveDronesFromXmlToDb(@RequestPayload MoveDronesFromXmlToDbRequest request) throws AuthException, OperationsException {
        MoveDronesFromXmlToDbResponse response = new MoveDronesFromXmlToDbResponse();

        List<Drone> drones = managerService.moveDronesFromXmlToDB();
        List<DroneSOAP> dronesSOAP = new ArrayList<>();

        for(Drone drone : drones)
        {
            DroneSOAP droneSOAP = convertDroneFromEneotyToSOAP(drone);
            dronesSOAP.add(droneSOAP);
        }

        response.getDrones().addAll(dronesSOAP);

        return response;
    }

    @PayloadRoot(namespace = url, localPart = "createDroneInDbRequest")
    @ResponsePayload
    public CreateDroneInDbResponse createDroneInDb(@RequestPayload CreateDroneInDbRequest request) throws AuthException {
        CreateDroneInDbResponse response = new CreateDroneInDbResponse();

        DroneDTO droneDto = new DroneDTO();
        droneDto.setDroneName(request.getDroneName());
        droneDto.setBatteryCapacity(request.getBatteryCapacity());
        droneDto.setPrice(request.getPrice());
        droneDto.setDroneNew(request.isDroneNew());
        droneDto.setFramsShape(request.getFrameShape().charAt(0));
        droneDto.setAutonomus(request.isAutonomous());

        Drone drone = managerService.createDroneInDb(droneDto);
        DroneSOAP droneSOAP = convertDroneFromEneotyToSOAP(drone);

        response.setDrone(droneSOAP);

        return response;
    }

    @PayloadRoot(namespace = url, localPart = "updateDroneInDbRequest")
    @ResponsePayload
    public UpdateDroneInDbResponse updateDroneInDb(@RequestPayload UpdateDroneInDbRequest request) throws AuthException, InvalidObjectException {
        UpdateDroneInDbResponse response = new UpdateDroneInDbResponse();

        Drone drone = managerService.getFromDbByName(request.getDroneName());

        DroneDTO droneDto = new DroneDTO();
        droneDto.setDroneName(request.getNewName());
        droneDto.setBatteryCapacity(request.getBatteryCapacity());
        droneDto.setPrice(request.getPrice());
        droneDto.setDroneNew(drone.isDroneNew());
        droneDto.setFramsShape(drone.getFramsShape());
        droneDto.setAutonomus(drone.isAutonomus());

        drone = managerService.updateDroneInDb(request.getDroneName(), droneDto);
        DroneSOAP droneSOAP = convertDroneFromEneotyToSOAP(drone);

        response.setDrone(droneSOAP);

        return response;
    }

    @PayloadRoot(namespace = url, localPart = "deleteDroneFromDbRequest")
    @ResponsePayload
    public DeleteDroneFromDbResponse deleteDroneFromDb(@RequestPayload DeleteDroneFromDbRequest request) throws AuthException {
        DeleteDroneFromDbResponse response = new DeleteDroneFromDbResponse();

        Drone drone = managerService.deleteDroneFromDb(request.getDroneName());
        DroneSOAP droneSOAP = convertDroneFromEneotyToSOAP(drone);

        response.setDrone(droneSOAP);

        return response;

    }

    @PayloadRoot(namespace = url, localPart = "createPdfFileRequest")
    @ResponsePayload
    public CreatePdfFileResponse createPdfFile(@RequestPayload CreatePdfFileRequest request) throws FOPException, IOException, TransformerException {
        CreatePdfFileResponse response = new CreatePdfFileResponse();

        String message = managerService.convertToPdf();
        response.setMessage(message);

        return response;
    }

    //Helper metods

    public List<ManagerSOAP> toSoap(List<Manager> managers){
        List<ManagerSOAP> soapManagers = new ArrayList<>();

        for(Manager manager: managers){
            ManagerSOAP managerSOAP = convertManagerFromEntityToSOAP(manager);
            soapManagers.add(managerSOAP);
        }

        return soapManagers;
    }

    public ManagerSOAP convertManagerFromEntityToSOAP(Manager manager)
    {
        ManagerSOAP managerSOAP = new ManagerSOAP();
        List<DroneSOAP> dronesSOAP = new ArrayList<>();

        for(Drone drone : manager.getSoldDrones()){
            DroneSOAP droneSOAP = convertDroneFromEneotyToSOAP(drone);
            dronesSOAP.add(droneSOAP);
        }

        managerSOAP.setId(manager.getId());
        managerSOAP.setAccountNumber(manager.getAccountNumber());
        managerSOAP.setUsername(manager.getUsername());
        managerSOAP.getDrone().addAll(dronesSOAP);

        return managerSOAP;
    }

    public DroneSOAP convertDroneFromEneotyToSOAP(Drone drone)
    {
        DroneSOAP droneSOAP = new DroneSOAP();
        droneSOAP.setDroneName(drone.getDroneName());
        droneSOAP.setPrice(drone.getPrice());
        droneSOAP.setBatteryCapacity(drone.getBatteryCapacity());

        return droneSOAP;
    }

}
