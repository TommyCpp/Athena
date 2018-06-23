package com.athena.model.publication.search;

import com.athena.model.publication.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

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
            titlesIn = (root, criteriaQuery, criteriaBuilder) -> root.get("title").in((Object[]) title);
        }
        if (this.publisherName != null) {
            publisherNameIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("publisher").get("name"), publisherName);
        }
        if (this.language != null) {
            languageIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("language"), language);
        }
        if(this.author != null){
            hasAuthor = (root, criteriaQuery, criteriaBuilder) -> root.get("author").in((Object[]) author);
        }

        return Specifications.where(titlesIn).and(publisherNameIs).and(languageIs).and(hasAuthor);
    }

}
