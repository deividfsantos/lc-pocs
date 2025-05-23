package com.dsantos.chainofresponsability;

public interface DispenseChain {

    void setNextChain(DispenseChain nextChain);

    int dispense(Currency cur);
}
