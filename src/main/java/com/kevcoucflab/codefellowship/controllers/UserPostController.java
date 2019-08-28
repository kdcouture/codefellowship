package com.kevcoucflab.codefellowship.controllers;

import com.kevcoucflab.codefellowship.models.AppUser;
import com.kevcoucflab.codefellowship.models.AppUserRepository;
import com.kevcoucflab.codefellowship.models.UserPost;
import com.kevcoucflab.codefellowship.models.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;

@Controller
public class UserPostController {

    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    UserPostRepository userPostRepository;

    @GetMapping("/posts/create")
    public String getNewPostPage(Principal p, Model m) {
        m.addAttribute("p", p);
        return "createpost";
    }

    @PostMapping("/posts/create")
    public RedirectView createNewPost(String body, Principal p) {
        // Find user by username
        AppUser poster = appUserRepository.findByUsername(p.getName());
        // Create new post object
        UserPost post = new UserPost(body, poster);
        // Save new post into database
        userPostRepository.save(post);

        return new RedirectView("/myprofile");
    }
}
