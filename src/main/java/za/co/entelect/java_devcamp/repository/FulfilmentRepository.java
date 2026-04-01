package za.co.entelect.java_devcamp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.entelect.java_devcamp.entity.FulfilmentType;

public interface FulfilmentRepository extends JpaRepository<FulfilmentType, Long> {

}
