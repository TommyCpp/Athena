package com.athena.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by Tommy on 2017/9/10.
 */
@Entity
@Table(name = "audio")
public class Audio implements Publication {
    private String isrc;
    private String title;
    private String subtitle;
    private String author;
    private String translator;
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

    @Basic
    @Column(name = "author", nullable = false, length = 128)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "translator", nullable = false, length = 128)
    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
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

        if (isrc != null ? !isrc.equals(audio.isrc) : audio.isrc != null) return false;
        if (title != null ? !title.equals(audio.title) : audio.title != null) return false;
        if (subtitle != null ? !subtitle.equals(audio.subtitle) : audio.subtitle != null) return false;
        if (author != null ? !author.equals(audio.author) : audio.author != null) return false;
        if (translator != null ? !translator.equals(audio.translator) : audio.translator != null) return false;
        if (publishDate != null ? !publishDate.equals(audio.publishDate) : audio.publishDate != null) return false;
        if (coverUrl != null ? !coverUrl.equals(audio.coverUrl) : audio.coverUrl != null) return false;
        if (price != null ? !price.equals(audio.price) : audio.price != null) return false;
        if (titlePinyin != null ? !titlePinyin.equals(audio.titlePinyin) : audio.titlePinyin != null) return false;
        if (titleShortPinyin != null ? !titleShortPinyin.equals(audio.titleShortPinyin) : audio.titleShortPinyin != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = isrc != null ? isrc.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (subtitle != null ? subtitle.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (translator != null ? translator.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (coverUrl != null ? coverUrl.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (titlePinyin != null ? titlePinyin.hashCode() : 0);
        result = 31 * result + (titleShortPinyin != null ? titleShortPinyin.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id", nullable = false)
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisherId) {
        this.publisher = publisherId;
    }


    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToMany
    @JoinTable(name = "audio_copy",
            joinColumns = @JoinColumn(name = "isrc", table = "audio", referencedColumnName = "isrc"),
            inverseJoinColumns = @JoinColumn(name = "copy_id", table = "copy", referencedColumnName = "id")
    )
    public List<AudioCopy> getCopies() {

        return copies;
    }

    public void setCopies(List<AudioCopy> copies) {
        this.copies = copies;
    }
}
