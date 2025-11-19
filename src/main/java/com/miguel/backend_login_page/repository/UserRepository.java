package com.miguel.backend_login_page.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.backend_login_page.domain.user.User;

public interface UserRepository extends JpaRepository<User, String> {

}
