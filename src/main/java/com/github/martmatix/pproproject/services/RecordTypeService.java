package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.embeddable.RecordType;

import java.util.List;

public interface RecordTypeService {

    List<RecordType> findAllRecordTypes();

}
