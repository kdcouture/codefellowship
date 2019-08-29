package com.kevcoucflab.codefellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;

    String password;

    String firstName;
    String lastName;
    Date DOB;
    String bio;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "poster")
    List<UserPost> posts;

    @ManyToMany
    @JoinTable( // Mostly from in class
            // Database name
            name="followers",
            // join columns: column where I find my own ID
            joinColumns = { @JoinColumn(name="sheep") },
            // inverse: column where I find someone else's ID
            inverseJoinColumns = { @JoinColumn(name="leader") }
    )
    Set<AppUser> follows;

    @ManyToMany(mappedBy = "follows")
    Set<AppUser> followers;

    public AppUser(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = null;
        this.bio = "";
    }

    public AppUser() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDOB() {
        return DOB;
    }

    public String getBio() {
        return bio;
    }

    public List<UserPost> getPosts() {
        return posts;
    }

    public List<UserPost> getRecentPosts() {
        List<UserPost> recent = new LinkedList<UserPost>();
        for(int i = 0; i < 3; i++) {
            if(i >= this.posts.size()) {
                break;
            }
            recent.add(this.posts.get(this.posts.size()-1-i));
        }
        return recent;
    }

    public void addFollower(AppUser sheep) {
        followers.add(sheep);
    }

    public void joinHerd(AppUser leader) {
        follows.add(leader);
    }

    public Set<AppUser> getFollows() {
        return follows;
    }

    public Set<AppUser> getFollowers() {
        return followers;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
