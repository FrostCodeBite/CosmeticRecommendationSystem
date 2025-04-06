package com.cosmeticrecommend.sub;

import com.cosmeticrecommend.interfaces.IFilter;
import com.cosmeticrecommend.reponsitories.CosmeticResponsitory;

public class PriceFilter implements IFilter{

	private double minPrice;
    private double maxPrice;

    public PriceFilter(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
    
	@Override
	public boolean satisfies(String barcode) {
		double price = CosmeticResponsitory.getPrice(barcode);
        return (minPrice <= price) && (price <= maxPrice);
	}

}
