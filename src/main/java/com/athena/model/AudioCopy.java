package com.athena.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Tommy on 2017/9/10.
 */
@Entity
@Table(name = "audio_copy")
@SecondaryTable(name = "copy", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "copy_id"))
@AttributeOverride(name = "id", column = @Column(name = "copy_id",table = "audio_copy"))
public class AudioCopy extends Copy {
    private Audio audio;

    @Override
    @Id
    @GenericGenerator(name = "copy_id_generator", strategy = "increment")
    @GeneratedValue(generator = "copy_id_generator")
    public Long getId() {
        return super.getId();
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "isrc", table = "audio_copy", referencedColumnName = "isrc")
    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }
}
