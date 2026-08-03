package com.sdewa.BasicSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.sdewa.BasicSpring.models.OrderEntity;
import com.sdewa.BasicSpring.models.OrderQuery;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>, JpaSpecificationExecutor<OrderEntity> {
    public static Specification<OrderEntity> queryOrder(OrderQuery query) {
        return (root, q, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query == null) {
                return criteriaBuilder.conjunction();
            }

            String keyword = query.getKeyword();
            if (keyword != null && !keyword.isBlank()) {
                String keywordPattern = "%" + keyword.toLowerCase() + "%";
                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("number")), keywordPattern),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("notes")), keywordPattern)));
            }

            if (query.getIncludesIds() != null && !query.getIncludesIds().isEmpty()) {
                predicates.add(root.get("id").in(query.getIncludesIds()));
            }

            if (query.getExcludesIds() != null && !query.getExcludesIds().isEmpty()) {
                predicates.add(criteriaBuilder.not(root.get("id").in(query.getExcludesIds())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    
}
