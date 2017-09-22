package com.athena.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Tommy on 2017/9/10.
 */
@Entity
@Table(name = "copy")
public class AudioCopy extends Copy {
    private Audio audio;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinTable(name = "audio_copy",
            joinColumns = @JoinColumn(name = "copy_id", table = "copy", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "isrc", table = "audio", referencedColumnName = "isrc", nullable = false)
    )
    @NotNull
    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio){
        this.audio = audio;
    }
}
