package com.cosmeticrecommend.sub;

import java.util.ArrayList;

import com.cosmeticrecommend.interfaces.IFilter;

public class AllFilters implements IFilter {
	
	ArrayList<IFilter> filters;
    
    public AllFilters() {
        filters = new ArrayList<IFilter>();
    }

    public void addFilter(IFilter f) {
        filters.add(f);
    }

	@Override
	public boolean satisfies(String id) {
		for(IFilter f : filters) {
            if (! f.satisfies(id)) {
                return false;
            }
        }
        
        return true;
	}

}
