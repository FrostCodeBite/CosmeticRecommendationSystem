package com.cosmeticrecommend.controller;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cosmeticrecommend.models.CosmeticModel;
import com.cosmeticrecommend.models.ListRatingModel;
import com.cosmeticrecommend.models.RatingModel;
import com.cosmeticrecommend.reponsitories.CosmeticRatingRepository;
import com.cosmeticrecommend.reponsitories.CosmeticResponsitory;
import com.cosmeticrecommend.reponsitories.RaterResponsitory;


@Controller
public class CosmeticController {
	
	@GetMapping("/survey")
	public static String showExistingCosmetic(Model model) {
		
		ListRatingModel ratingModel = new ListRatingModel();
//		HashMap<String, ArrayList<CosmeticModel>> hMap = new HashMap<String, ArrayList<CosmeticModel>>();

		ArrayList<CosmeticModel> arrayList = new ArrayList<CosmeticModel>();
		ArrayList<String> categoryList = new ArrayList<String>();
		
		ArrayList<CosmeticModel> cosmeticModels = CosmeticResponsitory.loadCosmeticList();
		for(int i = 0; i < cosmeticModels.size(); i++) {
			
			CosmeticModel m = cosmeticModels.get(i);
			String barcode = m.getBarcode();
			String imageLink = m.getImageLink();
			String productName = m.getProductName();
			String category = m.getCategory();
			String brand = m.getBrand();
			double price = m.getPrice();
			String ingredients = m.getIngredients();
			String skinConditions = m.getSkinConditions();
			String addProperties = m.getAddProperties();
			double ratingScore = m.getRatingScore();
			String raterID = "1000";
			double raterScore = 0.0;
			CosmeticModel cModel = new CosmeticModel(barcode, imageLink, productName, category, brand, price, ingredients, skinConditions, addProperties, ratingScore, raterID, raterScore);
			
//			if(hMap.containsKey(category)) {
//				hMap.get(category).add(cModel);
//			} else {
//				ArrayList<CosmeticModel> newArr = new ArrayList<CosmeticModel>();
//				newArr.add(cModel);
//				hMap.put(category, newArr);
//			}
			
			arrayList.add(cModel);
			if (!categoryList.contains(category)) {
				categoryList.add(category);
			}
		}
		ratingModel.setArrayList(arrayList);
		Collections.sort(arrayList);
		model.addAttribute("cosmetics",ratingModel);
		Collections.sort(categoryList);
		model.addAttribute("category",categoryList);
		
		return "existingCosmetics/survey";
	}
	
	@PostMapping("/survey")
	public static String createRating(@ModelAttribute("cosmetics") ListRatingModel ratingModel) { 

		//ADD RATING INFO INTO DATABASE
		ArrayList<RatingModel> arr = new ArrayList<RatingModel>();
		for (int i = 0 ; i < ratingModel.getArrayList().size(); i++) {
			if (ratingModel.getArrayList().get(i).getRaterScore() != 0.0) {
				String raterID = ratingModel.getArrayList().get(i).getRaterID();
				String barcode = ratingModel.getArrayList().get(i).getBarcode();
				double raterScore = ratingModel.getArrayList().get(i).getRaterScore();
				arr.add(new RatingModel(raterID, barcode, raterScore));
			}
		}
		RaterResponsitory.insertRatingDB(arr);

		
		//DIRECT USER TO URL: COSMETICS AFTER SURVEY
		return "redirect:/cosmetics";
	}
	
	@GetMapping("/cosmetics")
	public static String getRecommendCosmetics(Model model) {
		
		//USE COLLABORATIVE FILTERING TO GET RECOMMENDATION
		CosmeticRatingRepository repo = new CosmeticRatingRepository();
		int numSimilarRaters = 100;
		int minimalRaters = 1;
		ArrayList<RatingModel> ratingCosmetic = repo.getSimilarRatings("1000", numSimilarRaters, minimalRaters);
		ArrayList<CosmeticModel> recommendCosmetic = new ArrayList<CosmeticModel>();
		for(RatingModel m: ratingCosmetic) {
			recommendCosmetic.add(new CosmeticModel(
					m.getItem(),
					CosmeticResponsitory.getCosmetics(m.getItem()).getImageLink(),
					CosmeticResponsitory.getCosmetics(m.getItem()).getProductName(),
					CosmeticResponsitory.getCosmetics(m.getItem()).getCategory(),
					CosmeticResponsitory.getCosmetics(m.getItem()).getBrand(),
					CosmeticResponsitory.getCosmetics(m.getItem()).getPrice(),
					CosmeticResponsitory.getCosmetics(m.getItem()).getIngredients(),
					CosmeticResponsitory.getCosmetics(m.getItem()).getRatingScore(),
					m.getValue()
					));
		}
		model.addAttribute("recommend",recommendCosmetic);
		
		//DELETE RATING INFO FROM DATABASE
		RaterResponsitory.deleteRatingDB("1000");
		
		return "existingCosmetics/results";
	}

}
