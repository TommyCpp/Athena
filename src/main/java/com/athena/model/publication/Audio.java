package com.athena.model.publication;

import com.athena.model.copy.AudioCopy;
import com.athena.model.listener.AudioListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by Tommy on 2017/9/10.
 */
@Entity
@EntityListeners(AudioListener.class)
@Table(name = "audio")
public class Audio implements Publication {
    private String isrc;
    private String title;
    private String subtitle;
    private List<String> author;
    private List<String> translator;
    private Date publishDate;
    private String coverUrl;
    private Double price;
    private String titlePinyin;
    private String titleShortPinyin;
    private String language;

    private Publisher publisher;
    private List<AudioCopy> copies;

    @Id
    @Column(name = "isrc", nullable = false, length = 14)
    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 128)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "subtitle", nullable = false, length = 128)
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @ElementCollection
    @Column(name = "author_name")
    @CollectionTable(name = "audio_author", joinColumns = @JoinColumn(name = "isrc", referencedColumnName = "isrc"))
    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    @ElementCollection
    @Column(name = "translator_name")
    @CollectionTable(name = "audio_translator", joinColumns = @JoinColumn(name = "isrc", referencedColumnName = "isrc"))
    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
    }

    @Basic
    @Column(name = "publish_date", nullable = false)
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Basic
    @Column(name = "cover_url", nullable = true, length = 128)
    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "title_pinyin", nullable = true, length = 256)
    public String getTitlePinyin() {
        return titlePinyin;
    }

    public void setTitlePinyin(String titlePinyin) {
        this.titlePinyin = titlePinyin;
    }

    @Basic
    @Column(name = "title_short_pinyin", nullable = true, length = 16)
    public String getTitleShortPinyin() {
        return titleShortPinyin;
    }

    public void setTitleShortPinyin(String titleShortPinyin) {
        this.titleShortPinyin = titleShortPinyin;
    }

    @Basic
    @Column(name = "language", nullable = false)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Audio audio = (Audio) o;

        return audio.isrc.equals(this.isrc);
    }

    @Override
    public int hashCode() {
        return isrc != null ? isrc.hashCode() : 0;
    }

    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id", nullable = false)
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisherId) {
        this.publisher = publisherId;
    }


    @OneToMany(mappedBy = "audio")
    @JsonIgnore
    public List<AudioCopy> getCopies() {
        return copies;
    }

    public void setCopies(List<AudioCopy> copies) {
        this.copies = copies;
    }
}
