package com.athena.model.publication.search;

import com.athena.model.publication.Audio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * Created by Tommy on 2018/3/22.
 */
public class AudioSearchVo extends AbstractPublicationSearchVo<Audio> {
    @Override
    @JsonIgnore
    public Specification getSpecification() {
        Specification<Audio> titlesIn = this.getDefaultSpecifications();
        Specification<Audio> languageIs = this.getDefaultSpecifications();
        Specification<Audio> publisherNameIs = this.getDefaultSpecifications();

        if (this.titles != null) {
            titlesIn = (root, criteriaQuery, criteriaBuilder) -> root.get("title").in((Object[]) titles);
        }
        if (this.publisherName != null) {
            publisherNameIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("publisher").get("name"), publisherName);
        }
        if (this.language != null) {
            languageIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("language"), language);
        }

        return Specifications.where(titlesIn).and(publisherNameIs).and(languageIs);
    }
}
