package com.kislichenko.news.dao;

import com.kislichenko.news.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
