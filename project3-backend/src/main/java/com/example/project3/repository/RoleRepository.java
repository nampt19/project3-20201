package com.example.project3.repository;

import com.example.project3.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {
   Role findByName(String name);
   Role findById(int id);
   List<Role> findAllByName(String name);
}
