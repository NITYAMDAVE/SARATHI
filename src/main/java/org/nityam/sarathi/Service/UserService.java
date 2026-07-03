package org.nityam.sarathi.Service;

import org.nityam.sarathi.Model.User;
import org.nityam.sarathi.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public String login(String username, String password) {
        User user = userRepo.findByUsername(username);
        if(user == null){
            return "Username is invalid";
        }
        String repoPassword = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String encodedPassword = encoder.encode(password);
        if (repoPassword.equals(password)) {
            return "Hello Welcome Back";
        }
        return "Password is invalid";
    }

    public String register(User user) {
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
}
