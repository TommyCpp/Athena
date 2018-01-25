package com.athena.repository.mongo;

import com.athena.model.copy.CopyDamageReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tommy on 2017/11/30.
 */
@Repository
public interface CopyDamageReportRepository extends MongoRepository<CopyDamageReport, String>,CustomCopyDamageReportRepository {
}
