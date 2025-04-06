package com.cosmeticrecommend.sub;

import com.cosmeticrecommend.interfaces.IFilter;
import com.cosmeticrecommend.reponsitories.CosmeticResponsitory;

public class IngredientsFilter implements IFilter{

	 private String ingredient; 

    public IngredientsFilter(String ingredient) {
        this.ingredient = ingredient;
    }
	    
  //IF TRUE, IF THERE IS AT LEAST ONE INGREDIENT PRESENT IN HASHMAP<STRING, COSMETICS>
	@Override
	public boolean satisfies(String barcode) {
		String[] ingredientList = ingredient.split(",");
		for(int i = 0; i < ingredientList.length; i++) {
		    if (CosmeticResponsitory.getIngredients(barcode).toLowerCase().contains(ingredientList[i].trim().toLowerCase())) {
		        return true;
		   }
		}
		return false;
	}

}
