package com.kislichenko.news.dao;

import com.kislichenko.news.entity.AdBlockTypes;
import com.kislichenko.news.entity.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Long> {
    Price findPriceByType(AdBlockTypes type);
}
