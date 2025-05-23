package com.dsantos.chainofresponsability;

public class ATMDispenseChain {

    private final DispenseChain c1;

    public ATMDispenseChain() {
        this.c1 = new Dollar50Dispenser();
        DispenseChain c2 = new Dollar20Dispenser();
        DispenseChain c3 = new Dollar10Dispenser();

        // set chain
        c1.setNextChain(c2);
        c2.setNextChain(c3);
    }

    public DispenseChain getC1() {
        return c1;
    }
}