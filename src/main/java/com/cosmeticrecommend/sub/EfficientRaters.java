package com.cosmeticrecommend.sub;

import java.util.ArrayList;
import java.util.HashMap;

import com.cosmeticrecommend.interfaces.IRaters;
import com.cosmeticrecommend.models.RatingModel;

public class EfficientRaters implements IRaters{
	
	private String myID;
    HashMap<String, RatingModel> myHM;

    public EfficientRaters(String myID) {
        this.myID = myID;
        myHM = new HashMap<String, RatingModel>();
    } 

	@Override
	//ADD NEW RATER INTO HASHMAP<STRING, RATER> WHICH RATER HERE REFER TO HASHMAP<STRING, RATING>
    public void addRating(String item, double rating) {
        RatingModel rate = new RatingModel(item, rating);
        myHM.put(item, rate);
    }

	@Override
	//CHECK IF STRING ALSO EXIST IN HASHMAP<STRING, RATING>
    public boolean hasRating(String item) {        
        return myHM.containsKey(item);
    }

	@Override
	//RETURN BARCODE
    public String getID() {
        return myID;
    }

	@Override
	//GET RATINGSCORE FROM HASHMAP<STRING, RATER>
    public double getRating(String item) {
        if (myHM.containsKey(item)) {
            return myHM.get(item).getValue();
        }
        
        return -1;
    }

	@Override
	//RETURN RATING SIZE
    public int numRatings() {
        return myHM.size();
    }

	@Override
	//RETURN BARCODE ARRAYLIST
    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for (String barcode : myHM.keySet()) {
            list.add(barcode);
        }
        return list; 
    }  

}
