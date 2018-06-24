package com.athena.model.publication.search;

import com.athena.model.publication.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2018/1/27.
 */
public class BookSearchVo extends AbstractPublicationSearchVo<Book> implements PublicationSearchVo<Book> {

    @Override
    @JsonIgnore
    public Specification<Book> getSpecification() {
        Specification<Book> titlesIn = this.getDefaultSpecifications();
        Specification<Book> publisherNameIs = this.getDefaultSpecifications();
        Specification<Book> languageIs = this.getDefaultSpecifications();
        Specification<Book> hasAuthor = this.getDefaultSpecifications();
        if (this.title != null) {
            titlesIn = (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> queries = new ArrayList(this.title.length);
                for (String title : this.title) {
                    queries.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
                }
                return criteriaBuilder.or(queries.toArray(new Predicate[queries.size()]));
            };
        }
        if (this.publisherName != null) {
            publisherNameIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("publisher").get("name"), publisherName);
        }
        if (this.language != null) {
            languageIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("language"), language);
        }
        if (this.author != null) {
            hasAuthor = (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> queries = new ArrayList<>();
                for (String author : this.author) {
                    queries.add(criteriaBuilder.isMember(author, root.get("author")));
                }
                return criteriaBuilder.or(queries.toArray(new Predicate[queries.size()]));
            };
        }

        return Specifications.where(titlesIn).and(publisherNameIs).and(languageIs).and(hasAuthor);
    }

}
