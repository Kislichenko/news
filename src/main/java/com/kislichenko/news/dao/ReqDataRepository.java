package com.kislichenko.news.dao;

import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.ReqData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ReqDataRepository extends JpaRepository<ReqData, Long> {
}
