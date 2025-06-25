package com.example.desmosecommerce.service;

import com.example.desmosecommerce.entity.Product;
import java.util.Arrays;
import java.util.List;

public class AndCriteria implements SearchCriteria {
    private final List<SearchCriteria> criteriaList;
    public AndCriteria(SearchCriteria... criteria) {
        this.criteriaList = Arrays.asList(criteria);
    }
    @Override
    public boolean matches(Product product) {
        return criteriaList.stream().allMatch(c -> c.matches(product));
    }
} 