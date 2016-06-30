package com.framework;

public interface IFactory {
    <A> void stackContainer(IContainer container, String name);
    <A> void stack(A asset, String name);
    <A> A request(String name);
}
