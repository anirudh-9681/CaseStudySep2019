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
        //Holds a list of predicates to be used in conjunction
        List<Predicate> predicates = new ArrayList<>();

        if (map.containsKey("name")){
            String name = map.get("name").trim();
            for (String s: name.split(" ")){
                predicates.add(criteriaBuilder.like(root.get("name"),"%"+s.trim()+"%"));
            }
        }

        //checks if request has minPrice property and adds a predicate
        if (map.containsKey("minPrice")) {
            predicates.add(criteriaBuilder.ge(root.get("price"),
                    Integer.parseInt(map.get("minPrice"))));
        }

        //checks if request has maxPrice property and adds a predicate
        if (map.containsKey("maxPrice")) {
            predicates.add(criteriaBuilder.le(root.get("price"),
                    Integer.parseInt(map.get("maxPrice"))));
        }

        //checks if request has category property and adds a predicate
        if (map.containsKey("category") && !map.get("category").equalsIgnoreCase("all")) {
            predicates.add(criteriaBuilder.equal(root.get("category"), map.get("category")));
        }

        //checks if request has subcategory property
        if (map.containsKey("subcategory")) {
            String subcategory = map.get("subcategory");

            //check if subcategory is given in square brackets as array and removes the brackets
            if (subcategory.matches("^\\[.*]$")) {
                subcategory = subcategory.substring(1, subcategory.length() - 1);
            }

            // splits the subcategory property into different subcategories separated by commas.
            for (String s : subcategory.split(",")) {

                //checks against a pattern so the extra '%' signs. Also trims string to remove white spaces
                predicates.add(criteriaBuilder.like(root.get("subcategory"), "%" + s.trim() + "%"));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
