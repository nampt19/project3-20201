package com.example.project3.repository;

import com.example.project3.model.RolePageAction;
import com.example.project3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePageActionRepository  extends JpaRepository<RolePageAction,Integer> {
    List<RolePageAction> findAllByIdRole(int id);
        RolePageAction findById(int id);
}
