package com.dsantos.visitor;

public interface ShoppingCartVisitor {

    int visit(Book book);

    int visit(Fruit fruit);
}