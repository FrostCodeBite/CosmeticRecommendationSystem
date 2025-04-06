package com.cosmeticrecommend.reponsitories;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.cosmeticrecommend.interfaces.IRaters;
import com.cosmeticrecommend.models.RatingModel;
import com.cosmeticrecommend.sub.EfficientRaters;

@Repository
public class RaterResponsitory {
	
	private static HashMap<String, IRaters> ourRaters; 
	
	@Autowired
	private static JdbcTemplate jdbcTemplate;
	
	public RaterResponsitory(JdbcTemplate jdbcTemplate) {
		RaterResponsitory.jdbcTemplate = jdbcTemplate;
    }
	
	//CONNECT WITH DATABASE WITH PROVIDED FILENAME
    public static void initialize() {
        if (ourRaters == null) {
            ourRaters = new HashMap<String, IRaters>();
            addRating();
        } else {
        	ourRaters.clear();
            addRating();
        }
    }
	
	public static void addRating() {

		String sql = "SELECT * FROM raterdataset";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
		
		while(rows.next()) {
			String raterID = rows.getString("RaterID");
			String barcode = rows.getString("Barcode");
			double ratingScore = rows.getDouble("RatingScore");
			addRaterRating(raterID, barcode, ratingScore);
		}
	}
	
	//ADD RATERID AS STRING AND RATING AS CLASS OBJ FOR HASHMAP<STRING, RATER> 
    public static void addRaterRating(String raterID, String barcode, double rating) {
        IRaters rater =  null;
                //CHECK IF RATERID ALREADY EXIST, THEN GET PREVIOUS RATING INFORMATION
                if (ourRaters.containsKey(raterID)) {
                    rater = ourRaters.get(raterID); 
                } 
                //IF NOT EXIST, CREATE A NEW RATERID
                else { 
                    rater = new EfficientRaters(raterID);
                    ourRaters.put(raterID,rater);
                }
                //ADD NEW RATING
                rater.addRating(barcode,rating);
    } 
    
//    public static RatingModel getRatingDB(int id) {
//    	String sql = "SELECT * FROM raterdataset WHERE id=?";
//    	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);
//    	if (rows.next()) {
//			RatingModel model = new RatingModel();
//			model.setItem(rows.getString("RaterID"));
//			model.setItem2(rows.getString("Barcode"));			
//			model.setValue(rows.getDouble("RatingScore"));
//		}
//    	return null;
//    }
    
    //ADD RATING INTO DATABASE
    public static void insertRatingDB(ArrayList<RatingModel> ratingModels) {
    	String sql = "INSERT INTO raterdataset (RaterID, Barcode, RatingScore) "
    			+ "VALUES (?, ?, ?)";
    	for(RatingModel model : ratingModels) {
    		jdbcTemplate.update(sql, model.getItem(), model.getItem2(), model.getValue());
    	}
    }
    
    //DELTE RATING FROM DATABASE
    public static void deleteRatingDB(String raterID) {
    	String sql = "DELETE FROM raterdataset WHERE RaterID=?";
    	jdbcTemplate.update(sql, raterID);
    }
    
    
    //CHECK RATER SIZE  
    public static int size() {
        return ourRaters.size();
    }

    //RETURN RATER_ID
    public static IRaters getRater(String id) {
        return ourRaters.get(id);
    }

    //RETURN RATER_ID ARRAYLIST
    public static ArrayList<IRaters> getRaters() {
        ArrayList<IRaters> list = new ArrayList<IRaters>(ourRaters.values());
        return list;
    }

	
}
