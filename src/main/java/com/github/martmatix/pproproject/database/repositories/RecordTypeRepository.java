package com.github.martmatix.pproproject.database.repositories;

import com.github.martmatix.pproproject.database.entities.embeddable.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordTypeRepository extends JpaRepository<RecordType, Long> {

    List<RecordType> findAllByClosed(boolean closed);

}
