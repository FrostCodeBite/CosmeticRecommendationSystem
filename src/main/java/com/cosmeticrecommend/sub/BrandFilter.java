package com.cosmeticrecommend.sub;

import com.cosmeticrecommend.interfaces.IFilter;
import com.cosmeticrecommend.reponsitories.CosmeticResponsitory;

public class BrandFilter implements IFilter{

	private String brand; 

    public BrandFilter(String brand) {
        this.brand = brand;
    }
    
	@Override
	public boolean satisfies(String barcode) {
		String[] brandList = brand.split(",");
        for(int i = 0; i < brandList.length; i++) {
            if (CosmeticResponsitory.getBrand(barcode).toLowerCase().contains(brandList[i].trim().toLowerCase())) {
                return true;
           }
        }
        return false;
	}

}
