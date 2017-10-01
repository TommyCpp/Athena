package com.athena.service.copy;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.IllegalEntityAttributeExcpetion;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.exception.MixedCopyTypeException;
import com.athena.model.Audio;
import com.athena.model.AudioCopy;
import com.athena.repository.jpa.AudioRepository;
import com.athena.repository.jpa.copy.AudioCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
@Service
public class AudioCopyService implements CopyService<AudioCopy, Long, String> {

    private final AudioCopyRepository audioCopyRepository;
    private final AudioRepository audioRepository;


    @Autowired
    public AudioCopyService(AudioCopyRepository audioCopyRepository, AudioRepository audioRepository) {
        this.audioCopyRepository = audioCopyRepository;
        this.audioRepository = audioRepository;
    }

    @Override
    public void addCopy(AudioCopy copy) {
        this.audioCopyRepository.save(copy);
    }

    @Override
    public void addCopies(List<AudioCopy> copies) {
        this.audioCopyRepository.save(copies);
    }

    @Override
    public AudioCopy getCopy(Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        AudioCopy copy = this.audioCopyRepository.findOne(id);
        if (copy == null) {
            throw new IdOfResourceNotFoundException();
        }
        return copy;
    }

    @Override
    public List<AudioCopy> getCopies(List<Long> idList) {
        return this.audioCopyRepository.findAll(idList);
    }

    @Override
    public List<AudioCopy> getCopies(String fkList) throws IdOfResourceNotFoundException {
        Audio audio = this.audioRepository.findOne(fkList);
        if (audio == null) {
            throw new IdOfResourceNotFoundException();
        }
        return this.audioCopyRepository.findByAudio(audio);
    }

    @Override
    public void deleteCopy(Long id) {
        this.audioCopyRepository.delete(id);
    }

    @Override
    public void deleteCopies(List<Long> copyIdList) throws MixedCopyTypeException {
        List<AudioCopy> copyList = this.audioCopyRepository.findByIdInAndAudioIsNotNull(copyIdList);
        if (copyIdList.size() != copyList.size()) {
            throw new MixedCopyTypeException(AudioCopy.class);
        }
        this.audioCopyRepository.delete(copyList);
    }

    public void deleteCopies(String pk) throws IdOfResourceNotFoundException {
        this.audioCopyRepository.delete(this.getCopies(pk));
    }

    @Override
    public void updateCopy(AudioCopy copy) throws IllegalEntityAttributeExcpetion {
        try {
            this.audioCopyRepository.update(copy);
        } catch (Exception e) {
            throw new IllegalEntityAttributeExcpetion();
        }
    }

    @Override
    public void updateCopies(List<AudioCopy> copyList) throws IllegalEntityAttributeExcpetion {
        try {
            this.audioCopyRepository.update(copyList);
        } catch (Exception e) {
            throw new IllegalEntityAttributeExcpetion();
        }
    }
}
