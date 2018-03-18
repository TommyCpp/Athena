package com.athena.model.publication.search;

import com.athena.model.publication.Journal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * Created by Tommy on 2018/3/18.
 */
public class JournalSearchVo extends AbstractPublicationSearchVo<Journal> implements PublicationSearchVo<Journal> {
    private String issn;
    private Integer year;
    private Integer issue;

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    @Override
    @JsonIgnore
    public Specification getSpecification() {
        Specification<Journal> titlesIn = this.getDefaultSpecifications();
        Specification<Journal> publisherNameIs = this.getDefaultSpecifications();
        Specification<Journal> languageIs = this.getDefaultSpecifications();
        Specification<Journal> yearIs = this.getDefaultSpecifications();
        Specification<Journal> issnIs = this.getDefaultSpecifications();
        Specification<Journal> issueIs = this.getDefaultSpecifications();
        if (this.titles != null) {
            titlesIn = (root, criteriaQuery, criteriaBuilder) -> root.get("title").in((Object[]) titles);
        }
        if (this.publisherName != null) {
            publisherNameIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("publisher").get("name"), this.publisherName);
        }
        if (this.language != null) {
            languageIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("language"), this.language);
        }
        if (this.year != null) {
            yearIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("year"), this.year);
        }
        if (this.issue != null) {
            issueIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("issue"), this.issue);
        }
        if (this.issn != null) {
            issnIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("issn"), this.issn);
        }

        return Specifications.where(titlesIn).and(publisherNameIs).and(languageIs).and(issnIs).and(yearIs).and(issueIs);
    }
}
