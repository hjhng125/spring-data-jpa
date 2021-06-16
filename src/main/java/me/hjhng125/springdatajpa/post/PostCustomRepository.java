package me.hjhng125.springdatajpa.post;

import java.util.List;

public interface PostCustomRepository<T> {

    List<T> findMyPost();

    /**
     *     public void delete(T entity) {
     *         Assert.notNull(entity, "Entity must not be null!");
     *         if (!this.entityInformation.isNew(entity)) {
     *             Class<?> type = ProxyUtils.getUserClass(entity);
     *             T existing = this.em.find(type, this.entityInformation.getId(entity));
     *             if (existing != null) {
     *                 this.em.remove(this.em.contains(entity) ? entity : this.em.merge(entity));
     *             }
     *         }
     *     }*/
    // 실제로 jpa가 구현한 delete는 파라미터로 넘긴 객체가 영속성 컨텍스트에 존재하면 바로 rmove하지만
    // detach 상태인 경우 영속성 컨텍스트에 merge한 후 remove한다.
    // 이런 과정은 엔터티의 매핑되어 있는 연관 관계 때문 (cascade)
    // 이런 과정이 싫다면 아래처럼 새로 구현
    void delete(T entity);
}
