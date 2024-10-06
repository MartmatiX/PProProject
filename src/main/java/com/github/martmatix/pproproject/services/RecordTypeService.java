package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.embeddable.RecordType;

import java.util.List;
import java.util.Optional;

public interface RecordTypeService {

    List<RecordType> findAllRecordTypes();

    Optional<RecordType> findRecordById(Long id);

}
