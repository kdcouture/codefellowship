package com.kevcoucflab.codefellowship.controllers;

import com.kevcoucflab.codefellowship.models.AppUser;
import com.kevcoucflab.codefellowship.models.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppUserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AppUserRepository appUserRepository;

    @PostMapping("/users")
    public RedirectView createUser(String username, String password, String firstName, String lastName) {
        if(appUserRepository.findByUsername(username) == null) {
            AppUser newUser = new AppUser(username,
                    // bcrypt handles hashing/salting
                    encoder.encode(password),
                    firstName, lastName);
            appUserRepository.save(newUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new RedirectView("/myprofile");
        }
        return new RedirectView("/login");

    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public RedirectView loggedIn(String username, String password) {
        AppUser thisGuy = appUserRepository.findByUsername(username);
        if( thisGuy == null)
        {
            return new RedirectView("/signup");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(thisGuy, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/myprofile")
    public String getMyProfile(Principal p, Model m) {
        AppUser user = appUserRepository.findByUsername(p.getName());
        m.addAttribute("user", user);
        m.addAttribute("p", p);
        return "myprofile";
    }

    @GetMapping("/users")
    public String getAllUsers(Model m, Principal p) {
        List<AppUser> users = appUserRepository.findAll();
        m.addAttribute("p", p);
        m.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user/{id}")
    public String getOneAlbum(@PathVariable long id, Model m, Principal p) {
        AppUser user = appUserRepository.findById(id).get();
        m.addAttribute("p", p);
        m.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/feed")
    public String getFeed(Principal p, Model m) {
        AppUser user = appUserRepository.findByUsername(p.getName());
        m.addAttribute("user", user);
        m.addAttribute("p",p);
        return "feed";
    }

    @PostMapping("/addfollower")
    public RedirectView addFollower(long tofollow, String followerUName) {
        System.out.println(tofollow);
        // One directional follow, only our follow set and leaders followers set get updated.
        AppUser leader = appUserRepository.findById(tofollow).get();
        AppUser sheep = appUserRepository.findByUsername(followerUName);
        sheep.joinHerd(leader);

        appUserRepository.save(leader);

        return new RedirectView("/myprofile");
    }
}