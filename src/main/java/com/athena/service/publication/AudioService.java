package com.athena.service.publication;

import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.exception.http.ResourceNotDeletable;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.copy.AudioCopy;
import com.athena.model.publication.Audio;
import com.athena.repository.jpa.AudioRepository;
import com.athena.repository.jpa.copy.AudioCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
@Service
public class AudioService implements PublicationService<Audio, String> {

    private AudioRepository audioRepository;
    private AudioCopyRepository audioCopyRepository;

    @Autowired
    public AudioService(AudioRepository audioRepository, AudioCopyRepository audioCopyRepository) {
        this.audioRepository = audioRepository;
        this.audioCopyRepository = audioCopyRepository;
    }

    @Override
    @Transactional
    public List<Audio> get(Iterable<String> ids) {
        return this.audioRepository.findAll(ids);
    }

    @Override
    @Transactional
    public Audio update(Audio audio) throws ResourceNotFoundByIdException, IllegalEntityAttributeException {
        Objects.requireNonNull(audio);
        if (!this.audioRepository.exists(audio.getIsrc())) {
            throw new ResourceNotFoundByIdException();
        }
        return this.audioRepository.save(audio);
    }

    @Override
    @Transactional
    public void delete(Audio audio) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        Objects.requireNonNull(audio);
        if (!this.audioRepository.exists(audio.getIsrc())) {
            throw new ResourceNotFoundByIdException();
        }
        List<AudioCopy> notDeletable = this.audioCopyRepository.isNotDeletable(audio.getIsrc());
        if (!notDeletable.isEmpty()) {
            throw new ResourceNotDeletable(notDeletable);
        }
        // will delete copy cascade
        this.audioRepository.delete(audio);
    }

    @Override
    public void delete(Iterable<Audio> audios) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        Objects.requireNonNull(audios);
        List<Audio> notDeletable = StreamSupport.stream(audios.spliterator(), false).filter(audio -> !this.audioCopyRepository.isNotDeletable(audio.getIsrc()).isEmpty()).collect(Collectors.toList());
        if (notDeletable.size() > 0) {
            throw new ResourceNotDeletable(notDeletable);
        }

        this.audioRepository.delete(audios);
    }

    @Override
    @Transactional
    public Audio add(Audio audio) {
        return this.audioRepository.save(audio);
    }

    @Override
    @Transactional
    public List<Audio> add(Iterable<Audio> audios) {
        return this.audioRepository.save(audios);
    }

    @Override
    public Audio get(String id) throws ResourceNotFoundByIdException, InvalidCopyTypeException {
        Audio result = this.audioRepository.findOne(id);
        if (Objects.isNull(result)) {
            throw new ResourceNotFoundByIdException();
        }
        return result;
    }

}
