package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.onion.entity.Saleuser;

public interface SaleRepository extends JpaRepository<Saleuser, String> {

	
	@Query(value= "select * from SaleUser where Saleid=:Saleid and Salepwd=:Salepwd and Saleemail=:Saleemail",nativeQuery = true)
	Saleuser  findBySaleIdAndSalePwdAndSaleEmail(@Param("Saleid") String Saleid, @Param("Salepwd") String Salepwd,@Param("Saleemail") String Saleemail);

	@Query(value="select * from SaleUser",nativeQuery = true)
	List<Saleuser> findByAllList();
	

}
