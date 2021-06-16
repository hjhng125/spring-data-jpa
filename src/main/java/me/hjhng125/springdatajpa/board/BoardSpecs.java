package me.hjhng125.springdatajpa.board;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

public class BoardSpecs {

    public static Specification<Board> isBest() {
        return (Specification<Board>)
            (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.isTrue(root.get(Board_.best));
    }

    public static Specification<Board> isTitleTop() {
        return (Specification<Board>)
            (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get(Board_.title), "top");
    }
}
