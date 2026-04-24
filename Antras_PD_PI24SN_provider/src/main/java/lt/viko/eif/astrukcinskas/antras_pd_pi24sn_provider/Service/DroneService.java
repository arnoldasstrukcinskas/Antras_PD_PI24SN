package lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.Service;

import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.Drone;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.model.DroneList;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.repository.DronesXMLRepository;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.repository.Interfaces.DroneRepository;
import lt.viko.eif.astrukcinskas.antras_pd_pi24sn_provider.repository.Interfaces.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.management.OperationsException;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.security.InvalidKeyException;
import java.util.List;


/**
 * Servisas, atsakingas už drono operacijas duomenų bazėje.
 */
@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private DronesXMLRepository dronesXMLRepository;


    /**
     * Metodas pridedantis droną į duomenų bazę.
     * @param droneDTO Drono duomenų perdavimo objektas
     * @return Pridėto drono objektas
     */
    public Drone createDroneInDb(DroneDTO droneDTO) {

        Drone drone = new Drone();
        drone.setDroneName(droneDTO.getDroneName());
        drone.setBatteryCapacity(droneDTO.getBatteryCapacity());
        drone.setPrice(droneDTO.getPrice());
        drone.setDroneNew(droneDTO.getDroneNew());
        drone.setFramsShape(droneDTO.getFramsShape());
        drone.setAutonomus(droneDTO.isAutonomus());

        droneRepository.save(drone);

        return drone;
    }

    /**
     * Metodas atnaujinantis drono informaciją duomenų bazėje.
     * @param droneName Norimo atnaujinti drono pavadinimas
     * @param droneDTO Drono duomenų perdavimo objektas
     * @return Atnaujinto drono objektas
     */
    public Drone updateDroneInDb(String droneName, DroneDTO droneDTO) {
        Drone drone = droneRepository.findFirstByDroneName(droneName);
        drone.setDroneName(droneDTO.getDroneName());
        drone.setBatteryCapacity(droneDTO.getBatteryCapacity());
        drone.setPrice(droneDTO.getPrice());
        drone.setDroneNew(droneDTO.getDroneNew());
        drone.setFramsShape(droneDTO.getFramsShape());
        drone.setAutonomus(droneDTO.isAutonomus());

        droneRepository.save(drone);
        return drone;
    }

    /**
     * Metodas ištrinantis droną iš duomenų bazės
     * @param droneName Norimo ištrinti drono pavadinimas
     * @return Pašalinto drono objektas
     */
    public Drone deleteDroneFromDb(String droneName) {
        Drone drone = droneRepository.findFirstByDroneName(droneName);
        droneRepository.delete(drone);

        return drone;
    }


    /**
     * Metodas paimantis visus dronus iš duomenų bazės.
     * @return Grąžinamas dronų sąrašas
     */
    public List<Drone> getAllDronesFromDB() {
        return droneRepository.findAll();
    }

    /**
     * Metodas surandantis droną duomenų bazėje pagal pavadinimą.
     * @param droneName norimo surasti drono pavadinimas
     * @return Grąžinamas drono objektas
     */
    public Drone getDroneFromDbByName(String droneName) {
        return droneRepository.findFirstByDroneName(droneName);
    }

    ////////////////////////////XML SECTION///////////////////////////////

    /**
     * Metodas atnaujinantis drono inforamciją XML faile
     * @param droneName drono pavadinimas
     * @param droneDTO drono duomenų perdavimo objektas
     * @return Gražina atnaujintą droną
     * @throws OperationsException
     */
    public Drone updateDroneInXml(String droneName, DroneDTO droneDTO) throws OperationsException {
        Drone responseDrone = fromDtoToDrone(droneDTO);

        List<Drone> drones = dronesXMLRepository.getAllDrones().getDrones();
        int droneIndex = 0;

//        if(!drones.contains(responseDrone))
//        {
//            throw new OperationsException("Drone service: no such drone in list");
//        }

        for (Drone drone : drones) {
            if (drone.getDroneName().equals(droneName)) {
                drones.set(droneIndex, responseDrone);

                dronesXMLRepository.writeAllDronesToFile(drones);

                return responseDrone;
            }
            droneIndex++;
        }

        return null;
    }

    /**
     * Metodas, surandantis droną pagal pavadinimą XML faile.
     *
     * @param droneName norimo rasti drono pavadinimas.
     * @return Sėkmės atveju rasto drono objektas, nesėkmės - null reikšmė
     */
    public Drone getDroneFromXmlByName(String droneName) {
        List<Drone> dronesInXml = dronesXMLRepository.getAllDrones().getDrones();
        for (Drone drone : dronesInXml) {
            if (drone.getDroneName().equals(droneName)) {
                return drone;
            }
        }
        return null;
    }

    /**
     * Metodas, gražinantis visus dronus iš XML failo
     *
     * @return Xml faile esančių dronų sąrašas
     */
    public List<Drone> getAllDronesFromXml(){
        DroneList drones = dronesXMLRepository.getAllDrones();
        return drones.getDrones();
    }

    /**
     * Metodas perkeliantis visus dronus iš XML failo į duomenų bazę
     * @return Grąžinama žinutė apie veiksmo statusą.
     */
    public List<Drone> moveDronesFromXmlToDB() throws OperationsException {
        List<Drone> dronesInXml = getAllDronesFromXml();
        List<Drone> dronesInDb = getAllDronesFromDB();
        try {
            for (Drone droneInXml : dronesInXml) {
                if (!dronesInDb.contains(droneInXml)) {
                    DroneDTO droneDTO = fromDroneToDto(droneInXml);
                    var response = createDroneInDb(droneDTO);
                }
            }
        } catch (Exception e) {
            throw new OperationsException(String.format("Drone service: Failed to move drones from xml to db (%s_)", e));
        }

        return dronesInXml;
    }

    //    Helper classes for conversion.

    /**
     * Pagalbinis metodas konvertuojantis drono duomenų perdavimo objektą į drono objektą
     * @param droneDTO Drono duomenų pervadimo objektas
     * @return Grąžinama drono klasė
     */
    public Drone fromDtoToDrone(DroneDTO droneDTO) {
        Drone drone = new Drone();
        drone.setDroneName(droneDTO.getDroneName());
        drone.setBatteryCapacity(droneDTO.getBatteryCapacity());
        drone.setPrice(droneDTO.getPrice());
        drone.setDroneNew(droneDTO.getDroneNew());
        drone.setFramsShape(droneDTO.getFramsShape());
        drone.setAutonomus(droneDTO.isAutonomus());
        return drone;
    }

    /**
     * Pagalbinis metodas konvertuojantis drono objektą į duomenų perdavomi objektą.
     * @param drone Drono objektas
     * @return Grąžinamas drono duomenų perdavimo objektas
     */
    public DroneDTO fromDroneToDto(Drone drone) {
        DroneDTO droneDto = new DroneDTO();
        droneDto.setDroneName(drone.getDroneName());
        droneDto.setBatteryCapacity(drone.getBatteryCapacity());
        droneDto.setPrice(drone.getPrice());
        droneDto.setDroneNew(drone.isDroneNew());
        droneDto.setFramsShape(drone.getFramsShape());
        droneDto.setAutonomus(drone.isAutonomus());
        return droneDto;
    }
}
