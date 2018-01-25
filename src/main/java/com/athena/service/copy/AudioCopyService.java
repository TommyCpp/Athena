package com.athena.service.copy;

import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.exception.http.MixedCopyTypeException;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.copy.AudioCopy;
import com.athena.model.publication.Audio;
import com.athena.repository.jpa.AudioRepository;
import com.athena.repository.jpa.copy.AudioCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
@Service
public class AudioCopyService implements CopyService<AudioCopy, String> {

    private final AudioCopyRepository audioCopyRepository;
    private final AudioRepository audioRepository;


    @Autowired
    public AudioCopyService(AudioCopyRepository audioCopyRepository, AudioRepository audioRepository) {
        this.audioCopyRepository = audioCopyRepository;
        this.audioRepository = audioRepository;
    }

    @Override
    public AudioCopy add(AudioCopy copy) {
        return this.audioCopyRepository.save(copy);
    }

    @Override
    public List<AudioCopy> add(Iterable<AudioCopy> copies) {
        return this.audioCopyRepository.save(copies);
    }

    @Override
    public AudioCopy get(Long id) throws ResourceNotFoundByIdException, InvalidCopyTypeException {
        AudioCopy copy = this.audioCopyRepository.findOne(id);
        if (copy == null) {
            throw new ResourceNotFoundByIdException();
        }
        return copy;
    }

    @Override
    public List<AudioCopy> get(Iterable<Long> idList) {
        return this.audioCopyRepository.findAll(idList);
    }

    @Override
    public List<AudioCopy> getCopies(String fkList) throws ResourceNotFoundByIdException {
        Audio audio = this.audioRepository.findOne(fkList);
        if (audio == null) {
            throw new ResourceNotFoundByIdException();
        }
        return this.audioCopyRepository.findByAudio(audio);
    }

    @Override
    public void deleteById(Long id) {
        this.audioCopyRepository.delete(id);
    }

    @Override
    public void deleteById(List<Long> copyIdList) throws MixedCopyTypeException {
        List<AudioCopy> copyList = this.audioCopyRepository.findByIdInAndAudioIsNotNull(copyIdList);
        if (copyIdList.size() != copyList.size()) {
            throw new MixedCopyTypeException(AudioCopy.class);
        }
        this.audioCopyRepository.delete(copyList);
    }

    @Transactional
    public void deleteCopies(String pk) throws ResourceNotFoundByIdException {
        this.audioCopyRepository.delete(this.getCopies(pk));
    }

    @Override
    public AudioCopy update(AudioCopy copy) throws IllegalEntityAttributeException {
        try {
            return this.audioCopyRepository.update(copy);
        } catch (Exception e) {
            throw new IllegalEntityAttributeException();
        }
    }

    @Override
    public List<AudioCopy> update(Iterable<AudioCopy> copyList) throws IllegalEntityAttributeException {
        try {
            return this.audioCopyRepository.update(copyList);
        } catch (Exception e) {
            throw new IllegalEntityAttributeException();
        }
    }
}
