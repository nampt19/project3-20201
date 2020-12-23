package com.example.project3.repository;

import com.example.project3.model.HistoryCare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryCareRepository extends JpaRepository<HistoryCare,Integer> {
    List<HistoryCare> findAllByIdCustomer(int idCustomer);
}
