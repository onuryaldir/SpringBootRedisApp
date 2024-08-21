package org.development.springredisassignment.repository;

import org.development.springredisassignment.model.DataRecord;
import org.springframework.data.repository.CrudRepository;

public interface DataRecordRepository extends CrudRepository<DataRecord, String> {
    // Custom query methods can be added here if needed
}