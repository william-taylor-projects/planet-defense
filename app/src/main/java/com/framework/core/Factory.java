package com.framework.core;

import java.util.*;

import com.framework.IContainer;
import com.framework.IFactory;

@SuppressWarnings("unchecked")
public class Factory implements IFactory {
    private TreeMap<String, Object> sharableAssets;

    public Factory() {
        sharableAssets = new TreeMap<String, Object>();
    }

    @Override
    public <A> void stack(A asset, String name) {
        sharableAssets.put(name, asset);
    }

    @Override
    public <A> A request(String assetID) {
        return (A)sharableAssets.get(assetID);
    }

    @Override
    public void stackContainer(IContainer container, String name) {
        stack(container, name);
        container.stackSubObjects(this);
    }
}
