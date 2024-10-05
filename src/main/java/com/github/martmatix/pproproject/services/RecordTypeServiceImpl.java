package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.embeddable.RecordType;
import com.github.martmatix.pproproject.database.repositories.RecordTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordTypeServiceImpl implements RecordTypeService{

    private RecordTypeRepository recordTypeRepository;

    @Override
    public List<RecordType> findAllRecordTypes() {
        return recordTypeRepository.findAll();
    }

    @Autowired
    public void setRecordTypeRepository(RecordTypeRepository recordTypeRepository) {
        this.recordTypeRepository = recordTypeRepository;
    }
}
