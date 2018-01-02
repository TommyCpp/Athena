package com.athena.model;

import com.athena.model.listener.BookListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by tommy on 2017/3/28.
 */
@Entity
@EntityListeners(BookListener.class)
@Table(name = "book")
public class Book implements Publication {
    private Long isbn;
    private List<String> author;
    private List<String> translator;
    private Date publishDate;
    private String categoryId;
    private Integer version;
    private String coverUrl;
    private String preface;
    private String introduction;
    private String directory;
    private String title;
    private String titlePinyin;
    private String titleShortPinyin;
    private String subtitle;
    private String language;
    private Double price;

    private Publisher publisher;
    private List<BookCopy> copies;

    public Book() {

    }

    @Id
    @Column(name = "isbn", nullable = false, length = 32)
    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    @ElementCollection
    @Column(name = "author_name")
    @CollectionTable(name = "book_author", joinColumns = @JoinColumn(name = "isbn", referencedColumnName = "isbn"))
    public List<String> getAuthor() {
        return this.author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
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
    @Column(name = "category_id", nullable = false, length = 32)
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "version", nullable = false)
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
    @Column(name = "preface", nullable = true, length = -1)
    public String getPreface() {
        return preface;
    }

    public void setPreface(String preface) {
        this.preface = preface;
    }

    @Basic
    @Column(name = "introduction", nullable = true, length = -1)
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Basic
    @Column(name = "directory", nullable = true, length = -1)
    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
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
    @Column(name = "subtitle", nullable = true, length = 128)
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Basic
    @Column(name = "language", nullable = false, length = 32)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @ElementCollection
    @Column(name = "translator_name")
    @CollectionTable(name = "book_translator", joinColumns = @JoinColumn(name = "isbn", referencedColumnName = "isbn"))
    public List<String> getTranslator() {
        return this.translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @ManyToOne
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Basic
    @Column(name = "title_pinyin", length = 256)
    public String getTitlePinyin() {
        return titlePinyin;
    }

    public void setTitlePinyin(String titlePinYin) {
        this.titlePinyin = titlePinYin;
    }

    @Basic
    @Column(name = "title_short_pinyin", length = 32)
    public String getTitleShortPinyin() {
        return titleShortPinyin;
    }

    public void setTitleShortPinyin(String titleShortPinyin) {
        this.titleShortPinyin = titleShortPinyin;
    }


    @OneToMany(mappedBy = "book")
    @JsonIgnore
    public List<BookCopy> getCopies() {
        return copies;
    }

    public void setCopies(List<BookCopy> copies) {
        this.copies = copies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
