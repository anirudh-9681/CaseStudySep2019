package com.beehyv.case_study.utilities;

import com.beehyv.case_study.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilterSpecification implements Specification<Product> {

    private Map<String, String> map;

    public FilterSpecification(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (map.containsKey("minPrice")) {
            predicates.add(criteriaBuilder.ge(root.get("price"),
                    Integer.parseInt(map.get("minPrice"))));
        }
        if (map.containsKey("maxPrice")) {
            predicates.add(criteriaBuilder.le(root.get("price"),
                    Integer.parseInt(map.get("maxPrice"))));
        }
        if (map.containsKey("category")) {
            predicates.add(criteriaBuilder.equal(root.get("category"), map.get("category")));
        }

        if (map.containsKey("subcategory")) {
            String subcategory = map.get("subcategory");
            if (subcategory.matches("^\\[.*]$")) {
                subcategory = subcategory.substring(1, subcategory.length() - 1);
            }
            for (String s : subcategory.split(",")) {
                predicates.add(criteriaBuilder.like(root.get("subcategory"), s));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
