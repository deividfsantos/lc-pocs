package com.dsantos.visitor;

public interface ItemElement {

    int accept(ShoppingCartVisitor visitor);
}