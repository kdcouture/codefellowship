package com.kevcoucflab.codefellowship.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    public AppUser findByUsername(String username);
}
