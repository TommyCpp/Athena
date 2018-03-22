package com.athena.repository.jpa;

import com.athena.model.publication.Audio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
@Repository
public interface AudioRepository extends JpaRepository<Audio, String>, JpaSpecificationExecutor<Audio> {

}
