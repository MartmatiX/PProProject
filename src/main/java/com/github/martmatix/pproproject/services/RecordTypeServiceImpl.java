package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.embeddable.RecordType;
import com.github.martmatix.pproproject.database.repositories.RecordTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordTypeServiceImpl implements RecordTypeService{

    private RecordTypeRepository recordTypeRepository;

    @Override
    public List<RecordType> findAllRecordTypes() {
        return recordTypeRepository.findAll();
    }

    @Override
    public Optional<RecordType> findRecordById(Long id) {
        return recordTypeRepository.findById(id);
    }

    @Autowired
    public void setRecordTypeRepository(RecordTypeRepository recordTypeRepository) {
        this.recordTypeRepository = recordTypeRepository;
    }
}
