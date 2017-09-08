package com.athena.model;

import com.athena.service.PinyinConvertService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by tommy on 2017/3/28.
 */
@Entity
@Table(name = "book")
public class Book implements Publication {
    private Long isbn;
    private String _author;
    private String _translator;
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

    @Transient
    private PinyinConvertService pinyinConvertService;

    public Book() {
        this.pinyinConvertService = new PinyinConvertService();
    }

    @Id
    @Column(name = "isbn", nullable = false, length = 32)
    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "author", nullable = false, length = 128)
    @JsonIgnore
    public String get_author() {
        return this._author;
    }

    public void set_author(String _author) {
        this._author = _author;
    }

    @Transient
    public List<String> getAuthor() {
        return Arrays.asList(StringUtils.split(this._author, ","));
    }

    @Transient
    public void setAuthor(List<String> author) {
        if (author == null) {
            return;
        }
        String[] authors = new String[author.size()];
        author.toArray(authors);
        Arrays.sort(authors);
        this._author = StringUtils.join(authors, ",");
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
        this.setTitlePinyin(this.pinyinConvertService.getPinYin(title));
        this.setTitleShortPinyin(this.pinyinConvertService.getShortPinYin(title));
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

    @Basic
    @Column(name = "translator", length = 128)
    @JsonIgnore
    public String get_translator() {
        return this._translator;
    }

    public void set_translator(String _translator) {
        this._translator = _translator;
    }

    @Transient
    public List<String> getTranslator() {
        return this._translator == null ? new ArrayList<>() : Arrays.asList(StringUtils.split(this._translator, ","));
    }

    @Transient
    public void setTranslator(List<String> translator) {
        if (translator == null) {
            return;
        }
        String[] translators = new String[translator.size()];
        translator.toArray(translators);
        Arrays.sort(translators);
        this._translator = StringUtils.join(translators, ",");
    }

    @Basic
    @Column(name = "price", nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (_author != null ? !_author.equals(book._author) : book._author != null) return false;
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
        if (_translator != null ? !_translator.equals(book._translator) : book._translator != null) return false;
        if (price != null ? !price.equals(book.price) : book.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = isbn != null ? isbn.hashCode() : 0;
        result = 31 * result + (_author != null ? _author.hashCode() : 0);
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
        result = 31 * result + (_translator != null ? _translator.hashCode() : 0);
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


    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToMany
    @JoinTable(name = "book_copy",
            joinColumns = @JoinColumn(name = "isbn", table = "book", referencedColumnName = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "copy_id", table = "copy", referencedColumnName = "id")
    )
    @JsonIgnore
    public List<BookCopy> getCopies() {
        return copies;
    }

    public void setCopies(List<BookCopy> copies) {
        this.copies = copies;
    }
}
