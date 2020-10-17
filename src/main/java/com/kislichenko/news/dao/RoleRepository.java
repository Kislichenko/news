package com.kislichenko.news.dao;

import com.kislichenko.news.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
