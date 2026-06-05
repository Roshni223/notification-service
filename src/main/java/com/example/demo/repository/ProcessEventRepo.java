package com.example.demo.repository;

import com.example.demo.model.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessEventRepo extends JpaRepository<ProcessedEvent, String> {
}
//learning git and git hub commits