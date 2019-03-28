package com.example.springbootexperiment03.repository;

import com.example.springbootexperiment03.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("select a from Address a where a.detail=:detail")
    List<Address> list(@Param("detail") String detail);
}