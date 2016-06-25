package  com.planetDefense;

import java.util.*;

public class Factory implements IFactory {
	private class Asset {
		public String Name;
		public Object Item;
	}

	private Vector<Asset> SharableAssets;
	
	public Factory() {
		SharableAssets = new Vector<Asset>();
	}
	
	@Override
	public <A> void Stack(A asset, String name) {
		Asset Item = new Asset();
		Item.Item = asset;
		Item.Name = name;
		
		SharableAssets.add(Item);
	}
	
	@SuppressWarnings("unchecked")
	public <A> A DirectGrab(Integer stackLocation) {
		try {
			return(A)SharableAssets.get(stackLocation).Item; 
		} catch (Exception e) {
			ErrorLog.get().Write(e.getMessage());
			ErrorLog.get().Print();
			return null;
		}		
	}

	@Override @SuppressWarnings("unchecked")
	public <A> A Request(String assetID) {
		Integer Size = SharableAssets.size();
		Asset Asset = null;
		for(int i = 0; i < Size; i++) {
			Asset = SharableAssets.get(i);
			if(Asset.Name.equals(assetID)) {
				break;
			}
		} return(A)Asset.Item;
	}

	@Override
	public <A> void StackContainer(IContainer container, String name) {
		Stack(container, name);
		container.StackSubObjects(this);
	}
}
