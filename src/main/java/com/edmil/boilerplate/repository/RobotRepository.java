package com.edmil.boilerplate.repository;

import com.edmil.boilerplate.model.Robot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RobotRepository extends JpaRepository<Robot, Long> {

}
