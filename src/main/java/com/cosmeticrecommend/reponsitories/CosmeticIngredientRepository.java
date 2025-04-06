package com.cosmeticrecommend.reponsitories;

import java.util.ArrayList;
import java.util.Collections;

import com.cosmeticrecommend.models.CosmeticModel;
import com.cosmeticrecommend.sub.AllFilters;
import com.cosmeticrecommend.sub.BrandFilter;
import com.cosmeticrecommend.sub.IngredientsFilter;
import com.cosmeticrecommend.sub.PriceFilter;
import com.cosmeticrecommend.sub.TrueFilter;

public class CosmeticIngredientRepository {

	public CosmeticIngredientRepository() {
		CosmeticResponsitory.initialize();
	}
	
	public ArrayList<CosmeticModel> getFiltering(String ingredient, String brand, double minPrice, double maxPrice) {
		
        ArrayList<CosmeticModel> results = new ArrayList<CosmeticModel>();
        
        AllFilters af = new AllFilters();
        if (!ingredient.equals("")) {
            af.addFilter(new IngredientsFilter(ingredient));
        }
        if (minPrice > 0 && maxPrice > 0) {
            af.addFilter(new PriceFilter(minPrice, maxPrice));
        }
        if (!brand.equals("")) {
            af.addFilter(new BrandFilter(brand));
        } else {
            af.addFilter(new TrueFilter());
        }

        ArrayList<String> cosmetics = CosmeticResponsitory.filterBy(af);
        for (int i = 0; i < cosmetics.size(); i++) {
            results.add(CosmeticResponsitory.getCosmetics(cosmetics.get(i)));
        }
        Collections.sort(results, Collections.reverseOrder());
        return results;
    }
}
