package com.framework;

public interface IFactory {
    void stackContainer(IContainer container, String name);
    <A> void stack(A asset, String name);
    <A> A request(String name);
}
