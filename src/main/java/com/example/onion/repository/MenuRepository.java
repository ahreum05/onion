package com.example.onion.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.onion.entity.Menuitems;

public interface MenuRepository extends JpaRepository<Menuitems, Integer> {
    @Query(value = "select * from Menuitems where MIid=:MIid", nativeQuery = true)
    List<Menuitems> findByMIid(@Param("MIid") String MIid);
    
    @Query(value="SELECT AVG(TO_NUMBER(MIprice)) AS avg_price FROM Menuitems WHERE is_available = 1 and MIid=:MIid ", nativeQuery = true)
    Integer findByMIpriceAVG(@Param("MIid") String MIid);
    
    @Query(value="SELECT COUNT(*) FROM Menuitems WHERE is_available = 1 and  MIid=:id",nativeQuery = true)
    Integer findByCount(@Param("id") String id);
}
