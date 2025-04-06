package com.cosmeticrecommend.models;

public class RatingModel implements Comparable<RatingModel>{
	
	private String item;
	private String item2;
    private double value;
    
    public RatingModel () {}

    public RatingModel (String anItem, double aValue) {
        item = anItem;
        value = aValue;
    }
    
    public RatingModel (String anItem, String anItem2, double aValue) {
        item = anItem;
        item2 = anItem2;
        value = aValue;
    }

    public void setItem(String item) {
		this.item = item;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public void setValue(double value) {
		this.value = value;
	}

	// RETURN ITEM BEING RATED
    public String getItem () {
        return item;
    }
    
    // RETURN ITEM BEING RATED
    public String getItem2 () {
        return item2;
    }

    // RETURN THE VALUE OF THIS RATING
    public double getValue () {
        return value;
    }

    // RETURN STRING OF ALL INFORMATION
    public String toString () {
        return "[" + getItem() + ", " + getValue() + "]";
    }

	@Override
	public int compareTo(RatingModel other) {
		// TODO Auto-generated method stub
		if (value < other.value) return -1;
        if (value > other.value) return 1;
        
        return 0;
	}
	
	
}
