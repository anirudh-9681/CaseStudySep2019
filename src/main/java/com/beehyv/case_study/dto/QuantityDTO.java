package com.beehyv.case_study.dto;

public class QuantityDTO {

    private int quantity;

    public QuantityDTO(){}

    public QuantityDTO(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isValid(){
        return quantity >= 0;
    }
}
