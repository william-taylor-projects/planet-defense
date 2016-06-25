package com.planetDefense;

public interface IFactory {
	public <A> void StackContainer(IContainer container, String name);
	public <A> void Stack(A asset, String name);
	public <A> A Request(String name);
}
