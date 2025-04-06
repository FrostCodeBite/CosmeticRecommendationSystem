package com.cosmeticrecommend.sub;

import com.cosmeticrecommend.interfaces.IFilter;

public class TrueFilter implements IFilter {
	@Override
	public boolean satisfies(String id) {
		return true;
	}

}