package com.github.martmatix.pproproject.database.repositories;

import com.github.martmatix.pproproject.database.entities.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, Long> {

    List<RecordEntity> findAllByUserAndDate(String user, Date date);

}
