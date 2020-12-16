package com.kislichenko.news.dao;

import com.kislichenko.news.entity.ReqData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReqDataRepository extends JpaRepository<ReqData, Long> {
}
