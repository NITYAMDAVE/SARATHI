package org.nityam.sarathi.Service;

import org.nityam.sarathi.Model.Facility;
import org.nityam.sarathi.Model.Platform;
import org.nityam.sarathi.Model.Station;
import org.nityam.sarathi.Repo.FacilityRepo;
import org.nityam.sarathi.Repo.PlatformRepo;
import org.nityam.sarathi.Repo.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavigationService {

    @Autowired
    private StationRepo stationRepo;

    @Autowired
    private PlatformRepo platformRepo;

    @Autowired
    private FacilityRepo facilityRepo;

    public double navigater(String from, String to, int platformNumber, String station) {
        Station station1 = stationRepo.findByname(station);
        Platform platform=platformRepo.findByPlatformNumber(platformNumber);
        List<Facility> facilities=platform.getFacilities();
        Facility f=null;
        for (Facility facility : facilities) {
            if(facility.getName().equals(from)){
                f=facility;
            }
        }
        if(f==null){
            return -1;
        }
        for (Facility facility : facilities) {
            if(facility.getName().equals(to)){
                double distance=Math.sqrt(((f.getX()-facility.getX())*(f.getX()-facility.getX()))+((f.getY()-facility.getY())*(f.getY()-facility.getY())));
                return distance;
            }
        }
        return -1;
    };



    public String navigate(String from, String to, int platformNumber, String station) {
        Station station1 = stationRepo.findByname(station);
        Platform platform=platformRepo.findByPlatformNumber(platformNumber);
        List<Facility> facilities=platform.getFacilities();
        Facility f=null;
        for (Facility facility : facilities) {
            if(facility.getName().equals(from)){
                f=facility;
            }
        }
        if(f==null){
            return "Not Able to Get Your Location f is null";
        }
        for (Facility facility : facilities) {
            if(facility.getName().equals(to)){
                double distance=Math.sqrt(((f.getX()-facility.getX())*(f.getX()-facility.getX()))+((f.getY()-facility.getY())*(f.getY()-facility.getY())));
                return to+" is "+distance+" Meters Available From Your Location";

            }
        }

        List<Platform> platforms=station1.getPlatforms();
        Double miniDistance=Double.MAX_VALUE;
        double extra= navigater(from,"Stairs",platformNumber,station);
        Platform ans=null;
        for (Platform platform1 : platforms) {
            System.out.println(platform1.getPlatformNumber());
            System.out.println(station1.getCity());
            if(platform1.getPlatformNumber()!=platform.getPlatformNumber()){
                List<Facility> facilities1=platform1.getFacilities();
                for(Facility facility1 : facilities1){
                    if(facility1.getName().equals(to)){
                        double d1=Math.sqrt(((facility1.getX()-f.getX())*(facility1.getX()-f.getX()))+((facility1.getY()-f.getY())*(facility1.getY()-f.getY())));
                        double d2=(Math.abs(platformNumber -platform1.getPlatformNumber())*station1.getIntermediatePlatformDistance());
                        System.out.println("D2 is"+d2);
                        double d=d1+d2;
                        System.out.println(d);
                        System.out.println("Intermediate Distance:"+station1.getIntermediatePlatformDistance());
                        if(d<miniDistance){
                            miniDistance=d;
                            ans=platform1;
                        }
                    }
                }
            }
        }
        System.out.println(extra);
        if(ans==null){return "Not Able to Get Your Location All Searched";}
        return "Your Destination is on Platform Number "+ans.getPlatformNumber()+" which is "+miniDistance+extra+"Meters away from "+from;
    }
}
