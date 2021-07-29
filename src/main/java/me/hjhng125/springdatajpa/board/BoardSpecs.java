package me.hjhng125.springdatajpa.board;

import org.springframework.data.jpa.domain.Specification;

public class BoardSpecs {

    public static Specification<Board> isBest() {
        return (root, criteriaQuery, criteriaBuilder)
            -> criteriaBuilder.isTrue(root.get(Board_.best));
    }

    public static Specification<Board> isTitleTop() {
        return (root, criteriaQuery, criteriaBuilder)
            -> criteriaBuilder.like(root.get(Board_.title), "top");
    }
}
