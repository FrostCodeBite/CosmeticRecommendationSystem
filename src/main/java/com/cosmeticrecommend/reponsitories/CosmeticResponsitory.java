package com.cosmeticrecommend.reponsitories;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.cosmeticrecommend.interfaces.IFilter;
import com.cosmeticrecommend.models.CosmeticModel;

@Repository
public class CosmeticResponsitory {
	
	private static HashMap<String, CosmeticModel> ourCosmetics; 

	@Autowired
	private static JdbcTemplate jdbcTemplate;
	
	public CosmeticResponsitory(JdbcTemplate jdbcTemplate) {
        CosmeticResponsitory.jdbcTemplate = jdbcTemplate;
    }
	
	//CONNECT WITH DATABASE WITH PROVIDED FILENAME
    public static void initialize() {
        if (ourCosmetics == null) {
            ourCosmetics = new HashMap<String, CosmeticModel>();
            loadCosmetics();
        } else {
        	ourCosmetics.clear();
        	loadCosmetics();
        }
    }
	
	//ADD BARCODE & COSMETICS ARRAYLIST INTO HASHMAP<STRING, COSMETICS>
    public static void loadCosmetics() {
        ArrayList<CosmeticModel> list = loadCosmeticList();
        for (CosmeticModel m : list) {
            ourCosmetics.put(m.getBarcode(), m);
        }
    }
	
	//CALL FOR DATABASE AND ADD DATA INTO COSMETIC ARRAYLIST
	public static ArrayList<CosmeticModel> loadCosmeticList() {
		var cosmetics = new ArrayList<CosmeticModel>();
		
		String sql = "SELECT * FROM cosmeticdataset";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
		
		while(rows.next()) {
			CosmeticModel model = new CosmeticModel();
			model.setBarcode(rows.getString("Barcode"));
			model.setImageLink(rows.getString("ImageLink"));
			model.setProductName(rows.getString("ProductName"));
			model.setCategory(rows.getString("Category"));
			model.setBrand(rows.getString("Brand"));
			model.setPrice(rows.getDouble("Price"));
			model.setIngredients(rows.getString("Ingredients"));
			model.setSkinConditions(rows.getString("SkinCondition"));
			model.setAddProperties(rows.getString("AdditionalProperties"));
			model.setRatingScore(rows.getDouble("RatingScore"));
			cosmetics.add(model);
		}
		return cosmetics;
	}
	
	public static ArrayList<CosmeticModel> getRatingDB(int category) {
		ArrayList<CosmeticModel> results = new ArrayList<CosmeticModel>();
		
		String sql = "SELECT * FROM raterdataset WHERE Category=?";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, category);
		if (rows.next()) {
			CosmeticModel model = new CosmeticModel();
			model.setBarcode(rows.getString("Barcode"));
			model.setImageLink(rows.getString("ImageLink"));
			model.setProductName(rows.getString("ProductName"));
			model.setCategory(rows.getString("Category"));
			model.setBrand(rows.getString("Brand"));
			model.setPrice(rows.getDouble("Price"));
			model.setIngredients(rows.getString("Ingredients"));
			model.setSkinConditions(rows.getString("SkinCondition"));
			model.setAddProperties(rows.getString("AdditionalProperties"));
			model.setRatingScore(rows.getDouble("RatingScore"));
			results.add(model);
		}
		return results;
	}
	
	//CHECKING HASHMAP<STRING, COSMETICS> SIZE
    public static int size() {
        return ourCosmetics.size();
    }

    //RETURN INGREDIENTS FROM CERTAIN BARCODE
    public static String getIngredients(String barcode) {
        return ourCosmetics.get(barcode).getIngredients();
    }

    //RETURN PRICE FROM CERTAIN BARCODE
    public static double getPrice(String barcode) {
        return ourCosmetics.get(barcode).getPrice();
    }

    //RETURN BRAND FROM CERTAIN BARCODE
    public static String getBrand(String barcode) {
        return ourCosmetics.get(barcode).getBrand();
    }

    //RETURN THE WHOLE COSMETIC CLASS FROM CERTAIN BARCODE
    public static CosmeticModel getCosmetics(String barcode) {
        return ourCosmetics.get(barcode);
    }

    //CHECK IF COSMETIC INFO SATISTY CONDITION, THEN RETURN BARCODE ARRAYLIST
    public static ArrayList<String> filterBy(IFilter f) {
        ArrayList<String> list = new ArrayList<String>();
        for(String barcode : ourCosmetics.keySet()) {
            if (f.satisfies(barcode)) {
                list.add(barcode);
            }
        }
        
        return list;
    }
}
