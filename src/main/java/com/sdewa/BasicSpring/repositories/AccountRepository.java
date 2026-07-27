package com.sdewa.BasicSpring.repositories;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.sdewa.BasicSpring.models.AccountQuery;
import com.sdewa.BasicSpring.models.AccountsEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountsEntity, Long>,
        JpaSpecificationExecutor<AccountsEntity> {

    public static Specification<AccountsEntity> queryAccount(AccountQuery query) {
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
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), keywordPattern),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), keywordPattern),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("number")), keywordPattern)));
            }

            if (query.getIncludesIds() != null && !query.getIncludesIds().isEmpty()) {
                List<Long> includeIds = query.getIncludesIds();

                if (!includeIds.isEmpty()) {
                    predicates.add(root.get("id").in(includeIds));
                }
            }

            if (query.getExcludesIds() != null && !query.getExcludesIds().isEmpty()) {
                List<Long> excludeIds = query.getExcludesIds();

                if (!excludeIds.isEmpty()) {
                    predicates.add(criteriaBuilder.not(root.get("id").in(excludeIds)));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
