/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.store;

import java.io.IOException;

/**
 * This entity object implements a generic Store&#46; It has methods to load (add) {@link StoreItem}
 * into the Store and release {@link StoreItem} from the Store.
 * 
 * @see CashStore
 * @see CashStoreItem
 * @see Coin
 * @see DrinksBrand
 * @see DrinksStore
 * @see DrinksStoreItem
 * @see StoreController
 * @see StoreItem
 * @see StoreObject
 * 
 * @version 3.0 5/07/2003
 * @author Olivo Miotto, Pang Ping Li
 */
public abstract class Store {
	/**This constant attribute represent Cash*/
	public final static int CASH  = 1;
	/**This constant attribute represnet Drink*/
	public final static int DRINK = 2;
	/**This attribute hold the size of the store*/
	protected int size;
    /**This attribute hold the items of the store*/
	protected StoreItem items[];
	
	protected PropertyLoader propertyLoader;
	
	public Store(PropertyLoader propertyLoader) {
		this.propertyLoader = propertyLoader;
	}

	/**
	 * This constructor creates an instance of Store object.
	 */
	public Store() {
	}

	/**
	 * This constructor creates an instance of Store object.
	 * @param itemn the number of item.
	 */
	public Store(int itemn) {
		size = itemn;
		items = new StoreItem[size];
	}

        
	public abstract StoreIterator CreateIterator() ;

	/**
	 * This method sets the size of the items array in the Store.
	 * @param sz the store size.
	 */
	public void setStoreSize(int sz) {
		size = sz;
		items = new StoreItem[size];
	}

	/**
	 * This method returns the {@link StoreItem} corresponding to the index entered.
	 * @return the array of {@link StoreItem}.
	 */
	public StoreItem[] getItems() {
		return items;
	}

	/**
	 * This method adds {@link StoreItem} into the store.
	 * @param idx the index of the item.
	 * @param object the store item to be added.
	 */
	public void addItem(int idx, StoreItem object) {
		if ((idx >= size) || (idx < 0))
			return;
		items[idx] = object;
	}

	/**
	 * This method returns the {@link StoreItem} with the given index.
	 * @return the StoreItem.
	 */
	public StoreItem getStoreItem(int idx) {
		if ((idx >= size) || (idx < 0))
            return null;
		return items[idx];
	}

	/**
	 * This method finds a {@link StoreObject} in the store with a specified name&#46;
	 * @param name the name of the Store Object&#46;
	 * @return the Store Object&#46; Return null if no matching Store Object found&#46;
	 */
	public StoreObject findObject(String name) {
		String en;
		StoreObject so;
		int i;

		for (i = 0; i < size; i++) {
			if (items[i] == null)
				return null;
			so = items[i].getContent();
			if (so == null)
				return null;
			en = so.getName();
			if (en != null) {
				if (en.compareTo(name) == 0)
					return so;
			}
		}
		return null;
	}

	/**
	 * This method sets the total number of a store item held.
	 * @param idx the index of the store item.
	 * @param qty the quantity of the store item.
	 */
	public void setQuantity(int idx, int qty) {
		System.out.println("Store: setQauntity - qty=" + qty);
		if ((idx >= size) || (idx < 0))
			return;
		items[idx].setQuantity(qty);
	}

	/**
	 * This method return the store size; the total number of store item held.
	 * @return the store size.
	 */
	public int getStoreSize() {
		return size;
	}
	
	/**
	 * This method initialize the {@link Store}.
	 * @throws IOException if fail to initialize drinks store; reading properties.
	 */
	public abstract void initStore() throws IOException;
	
	/**
	 * This method saves the attributes of the {@link Store} to the input file.
	 * It saves the drink property when simulation is ended.
	 * @throws IOException if fail to save drinks properties.
	 */
	public void saveProps() throws IOException {
		int size = this.getStoreSize();
		propertyLoader.setNumOfItems(size);
		for (int i = 0; i < size; i++) {
			propertyLoader.setItem(i, this.getStoreItem(i));
		}
		propertyLoader.saveProperty();
	}
}//End of class Store