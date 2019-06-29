/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author fnogrett
 */
public class DiscountEntity {
    private char discount_code;
    private double discount_rate;

    public DiscountEntity(char discount_code, double discount_rate) {
        this.discount_code = discount_code;
        this.discount_rate = discount_rate;
    }

    public char getDiscount_code() {
        return discount_code;
    }

    public double getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_code(char discount_code) {
        this.discount_code = discount_code;
    }

    public void setDiscount_rate(double discount_rate) {
        this.discount_rate = discount_rate;
    }
  
    
}
