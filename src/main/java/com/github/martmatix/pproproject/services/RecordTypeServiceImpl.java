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
        return recordTypeRepository.findAllByOrderByClosed();
    }

    @Override
    public Optional<RecordType> findRecordById(Long id) {
        return recordTypeRepository.findById(id);
    }

    @Override
    public List<RecordType> findAllByClosed(boolean closed) {
        return recordTypeRepository.findAllByClosed(closed);
    }

    @Override
    public void save(RecordType recordType) {
        recordTypeRepository.save(recordType);
    }

    @Override
    public Optional<RecordType> findByName(String name) {
        return recordTypeRepository.findByName(name);
    }

    @Override
    public void deleteTicket(RecordType recordType) {
        recordTypeRepository.delete(recordType);
    }

    @Autowired
    public void setRecordTypeRepository(RecordTypeRepository recordTypeRepository) {
        this.recordTypeRepository = recordTypeRepository;
    }
}
