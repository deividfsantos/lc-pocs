//package com.dsantos;
//
//public class Year2024Tax implements Specification<Year> {
//    private TaxRequestModel taxRequestModel;
//    private Specification productSpecification;
//    private Specification stateSpecification;
//
//    public Year2024Tax(TaxRequestModel taxRequestModel) {
//        this.taxRequestModel = taxRequestModel;
//        this.productSpecification = new TaxProductSpecification(taxRequestModel.getProductCategory());
//        this.stateSpecification = new TaxStateSpecification(taxRequestModel.getState());
//    }
//
//    @Override
//    public boolean isSatisfiedBy(TaxModel candidate) {
//        return Year.of(2020).equals(candidate.getYear())
//                && productSpecification.and(stateSpecification).isSatisfiedBy(candidate);
//    }
//}
