package com.github.martmatix.pproproject.database.repositories;

import com.github.martmatix.pproproject.database.entities.embeddable.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordTypeRepository extends JpaRepository<RecordType, Long> {


}
