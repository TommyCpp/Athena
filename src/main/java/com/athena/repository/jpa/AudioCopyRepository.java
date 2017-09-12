package com.athena.repository.jpa;

import com.athena.model.Audio;
import com.athena.model.AudioCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
@Repository
public interface AudioCopyRepository extends JpaRepository<AudioCopy,Long> {
    List<AudioCopy> findByIdInAndAudioIsNotNull(List<Long> idList);

    AudioCopy findByIdAndAudioIsNotNull(Long id);

    List<AudioCopy> findByAudio(Audio audio);

}
