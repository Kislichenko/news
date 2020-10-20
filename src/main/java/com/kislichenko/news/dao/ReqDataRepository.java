package com.kislichenko.news.dao;

import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.ReqData;
import org.springframework.data.repository.CrudRepository;

public interface ReqDataRepository extends CrudRepository<ReqData, Long> {
}
