package com.athena.model.copy;

import com.athena.model.publication.Audio;
import com.athena.model.publication.Publication;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Tommy on 2017/9/10.
 */
@Entity
@Table(name = "audio_copy")
@PrimaryKeyJoinColumn(name = "copy_id", referencedColumnName = "id")
public class AudioCopy extends SimpleCopy implements PublicationCopy {
    private Audio audio;

    @ManyToOne
    @JoinColumn(name = "isrc", referencedColumnName = "isrc")
    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AudioCopy audioCopy = (AudioCopy) o;

        return audio != null ? audio.equals(audioCopy.audio) : audioCopy.audio == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (audio != null ? audio.hashCode() : 0);
        return result;
    }

    @Transient
    @Override
    @JsonIgnore
    public Publication getPublication() {
        return this.getAudio();
    }
}
