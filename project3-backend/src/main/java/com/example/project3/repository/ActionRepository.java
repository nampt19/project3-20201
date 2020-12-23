package com.example.project3.repository;

import com.example.project3.model.Action;
import com.example.project3.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository  extends JpaRepository<Action,Integer> {
}
