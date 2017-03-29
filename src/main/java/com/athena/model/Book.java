package com.athena.model;

import com.athena.model.conveter.WriterConverter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by tommy on 2017/3/28.
 *
 */
//TODO:测试连接关系
@Entity
@Table(name = "book")
public class Book {
    private Long isbn;
    @Convert(converter = WriterConverter.class)
    private List<String> author;
    @Convert(converter = WriterConverter.class)
    private List<String> translator;
    private Date publishDate;
    private String categoryId;
    private Integer version;
    private String coverUrl;
    private String preface;
    private String introduction;
    private String directory;
    private String title;
    private String subtitle;
    private String language;
    private Integer price;
    private Publisher publisher;

    @Id
    @Column(name = "isbn", nullable = false, length = 32)
    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    @ElementCollection
    @Column(name = "author", nullable = false, length = 128)
    public List<String> getAuthor() {
        return author;
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

    public void setSubtitle(String subtitile) {
        this.subtitle = subtitile;
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
    @Column(name = "translator", nullable = true, length = 128)
    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (publishDate != null ? !publishDate.equals(book.publishDate) : book.publishDate != null) return false;
        if (categoryId != null ? !categoryId.equals(book.categoryId) : book.categoryId != null) return false;
        if (version != null ? !version.equals(book.version) : book.version != null) return false;
        if (coverUrl != null ? !coverUrl.equals(book.coverUrl) : book.coverUrl != null) return false;
        if (preface != null ? !preface.equals(book.preface) : book.preface != null) return false;
        if (introduction != null ? !introduction.equals(book.introduction) : book.introduction != null) return false;
        if (directory != null ? !directory.equals(book.directory) : book.directory != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (subtitle != null ? !subtitle.equals(book.subtitle) : book.subtitle != null) return false;
        if (language != null ? !language.equals(book.language) : book.language != null) return false;
        if (translator != null ? !translator.equals(book.translator) : book.translator != null) return false;
        if (price != null ? !price.equals(book.price) : book.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = isbn != null ? isbn.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (coverUrl != null ? coverUrl.hashCode() : 0);
        result = 31 * result + (preface != null ? preface.hashCode() : 0);
        result = 31 * result + (introduction != null ? introduction.hashCode() : 0);
        result = 31 * result + (directory != null ? directory.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (subtitle != null ? subtitle.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (translator != null ? translator.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @ManyToOne
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    
}
