package com.kislichenko.news.dao;

import com.kislichenko.news.entity.Reportage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportageRepository extends JpaRepository<Reportage, Long> {
}
