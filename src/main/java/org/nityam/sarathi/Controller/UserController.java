package org.nityam.sarathi.Controller;


import org.nityam.sarathi.Configuration.JWTService;
import org.nityam.sarathi.Model.*;
import org.nityam.sarathi.Repo.*;
import org.nityam.sarathi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PlatformRepo platformRepo;
    @Autowired
    private StationRepo stationRepo;
    @Autowired
    private RegionRepo regionRepo;
    @Autowired
    private FacilityRepo facilityRepo;


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        if(authentication.isAuthenticated()){
            return "Token is :"+jwtService.getToken(username);
        }
        return "Invalid username or password";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user){

        String username = user.getUsername();
        User u= userRepo.findByUsername(username);
        if(u!=null){
            return "Try Another Username";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return "Register Successfully <br> Your  Username: " + user.getUsername()+"<br>Your Password: " + user.getPassword();
    }

    @PostMapping("/addPlatform")
    public String addPlatform(@RequestBody Platform platform){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            if(role.equals("ADMIN") || role.equals("PIC")){
                platformRepo.save(platform);
                return "Platform Added Successfully";
            }
        }
        return "Invalid Request";
    }

    @PostMapping("/addStation")
    public String addStation(@RequestBody Station station){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            if(role.equals("ADMIN") || role.equals("PIC")){
                stationRepo.save(station);
                return "Station Added Successfully" +
                        "Your Station Id is:"+station.getId();
            }
        }
        return "Invalid Request";
    }

    @PostMapping("/addRegion")
    public String addStation(@RequestBody Region region){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            if(role.toUpperCase().equals("ADMIN")){
                regionRepo.save(region);
                return "Region Added Successfully" +
                        "Your Region Id is:"+region.getId();
            }
        }
        return "Invalid Request";
    }

    @PostMapping("/addFacility")
    public String addFacility(@RequestBody Facility facility){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            if(role.equals("ADMIN") || role.equals("PIC")){
                facilityRepo.save(facility);
                return "Facility Added Successfully";
            }
        }
        return "Invalid Request";
    }

}
    