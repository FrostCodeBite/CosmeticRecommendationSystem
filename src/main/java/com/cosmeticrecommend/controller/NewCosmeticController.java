package com.cosmeticrecommend.controller;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cosmeticrecommend.models.CosmeticModel;
import com.cosmeticrecommend.models.NewSurveyModel;
import com.cosmeticrecommend.reponsitories.CosmeticIngredientRepository;
import com.cosmeticrecommend.reponsitories.CosmeticResponsitory;

import jakarta.validation.Valid;

@Controller
public class NewCosmeticController {
	
	private static String ingredients = "";
	private static String brand = "";
	private static double minPrice = 1.0;
	private static double maxPrice = 100.0;

	@GetMapping("/newSurvey")
	public static String showSurvey(Model model) {
		
		NewSurveyModel surveyModel = new NewSurveyModel();
		ArrayList<String> brandList = new ArrayList<String>();
		
		ArrayList<CosmeticModel> cosmeticModels = CosmeticResponsitory.loadCosmeticList();
		for(int i = 0 ; i < cosmeticModels.size(); i++) {
			String brand = cosmeticModels.get(i).getBrand();
			if (!brandList.contains(brand)) {
				brandList.add(brand);
//				System.out.println(brand);
			}
		}
		
		Collections.sort(brandList);
		model.addAttribute("brand",brandList);
		model.addAttribute("newSurvey",surveyModel);
		
		return "newCosmetics/newSurvey";
	}
	
	@PostMapping("/newSurvey")
	public static String sendSurvey(@Valid @ModelAttribute("newSurvey") NewSurveyModel surveyModel, BindingResult bindingResult) {
		
		if (surveyModel.getIngredients() == "") {
			bindingResult.addError(new FieldError("newSurvey", "ingredients", surveyModel.getIngredients(), false, null, null,"Please Select Your Skin Type!"));
		} else {
			ingredients = surveyModel.getIngredients();
		}
		
		if (surveyModel.getBrand() == null) {
			bindingResult.addError(new FieldError("newSurvey", "brand", surveyModel.getBrand(), false, null, null,"Please Select Brand!"));
		} else {
			brand = surveyModel.getBrand();
		}
		
		if (surveyModel.getMinPrice() < 0.5) {
			bindingResult.addError(new FieldError("newSurvey", "minPrice", surveyModel.getMinPrice(), false, null, null,"Please Set Min Price More Than 0.5$!"));
		} else if (surveyModel.getMinPrice() > surveyModel.getMaxPrice()) {
			bindingResult.addError(new FieldError("newSurvey", "minPrice", surveyModel.getMinPrice(), false, null, null,"Please Set Min Price Less Than Max Price"));
		} else {
			minPrice = surveyModel.getMinPrice();
		}
		
		if (surveyModel.getMaxPrice() < surveyModel.getMinPrice()) {
			bindingResult.addError(new FieldError("newSurvey", "maxPrice", surveyModel.getMaxPrice(), false, null, null,"Please Set Max Price More Than Min Price"));
		} else {
			maxPrice = surveyModel.getMaxPrice();
		}
		
		if(bindingResult.hasErrors()) {
			return "newCosmetics/newSurvey";
		}
		return "redirect:/newCosmetics";
	}
	
	@GetMapping("/newCosmetics")
	public static String getRecommendCosmetics(Model model) {
		
		CosmeticIngredientRepository repo = new CosmeticIngredientRepository();
		
		System.out.println(brand);
		
		ArrayList<CosmeticModel> recommendCosmetic = repo.getFiltering(ingredients, brand, minPrice, maxPrice);
		model.addAttribute("recommend",recommendCosmetic);
		
		return "existingCosmetics/results";
	}
}
