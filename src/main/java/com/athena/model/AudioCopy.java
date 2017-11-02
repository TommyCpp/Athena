package com.athena.model;

import javax.persistence.*;

/**
 * Created by Tommy on 2017/9/10.
 */
@Entity
@Table(name = "audio_copy")
@PrimaryKeyJoinColumn(name = "copy_id", referencedColumnName = "id")
public class AudioCopy extends SimpleCopy {
    private Audio audio;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "isrc", table = "audio_copy", referencedColumnName = "isrc")
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
}
