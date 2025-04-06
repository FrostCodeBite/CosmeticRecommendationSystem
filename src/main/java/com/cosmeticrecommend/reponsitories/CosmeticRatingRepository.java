package com.cosmeticrecommend.reponsitories;

import java.util.ArrayList;
import java.util.Collections;

import com.cosmeticrecommend.interfaces.IRaters;
import com.cosmeticrecommend.models.RatingModel;
import com.cosmeticrecommend.sub.TrueFilter;

public class CosmeticRatingRepository {

	public CosmeticRatingRepository() {
        CosmeticResponsitory.initialize();
        RaterResponsitory.initialize();
    }
	
	//CALCULATE THE DOTPRODUCT (CLOSENESS) BETWEEN ME AND ANOTHER PERSON
    public double dotProduct(IRaters me, IRaters r) {
        ArrayList<String> meItemList = me.getItemsRated();
        ArrayList<String> rItemList = r.getItemsRated();
        double productSum = 0.0;

        for (int i = 0; i < meItemList.size(); i++) {
            String meCurrentItem = meItemList.get(i);
            if (rItemList.contains(meCurrentItem)) {
                productSum += (me.getRating(meCurrentItem)-5) * (r.getRating(meCurrentItem)-5);
            }
        }

        return productSum;
    }

    //RETRIEVE RATING ARRAYLIST OF SIMILARITIES (CLOSENESS) BETWEEN ME AND OTHERS
    public ArrayList<RatingModel> getSimilarities(String rateID) {
        ArrayList<RatingModel> similar = new ArrayList<RatingModel>();

        IRaters me = RaterResponsitory.getRater(rateID);
        for (int i = 0; i < RaterResponsitory.size(); i++) {
            IRaters r = RaterResponsitory.getRaters().get(i);
            double product = dotProduct(me, r);
            //CHECK IF CLOSENESS >= 0
            if (product > 0) {
                //CHECK IF MY BARCODE THAT I RATE EQUAL TO ANOTHER PERSON BARCODE THAT RATED
                if (!me.equals(r)) {
                    similar.add(new RatingModel(r.getID(), product));
                }   
            }
        }
        //REVERSE ORDER FROM LARGEST TO SMALLEST SIMILARITYSCORE
        Collections.sort(similar, Collections.reverseOrder());
        return similar;
    }

    public ArrayList<RatingModel> getSimilarRatings(String raterID, int numSimilarRaters, int minimalRaters) {
        ArrayList<RatingModel> ratings = new ArrayList<RatingModel>();
        
        ArrayList<RatingModel> raterSimilar = new ArrayList<RatingModel>();
        raterSimilar = getSimilarities(raterID);
        ArrayList<String> cosmetics = CosmeticResponsitory.filterBy(new TrueFilter());

        for (int i = 0; i < cosmetics.size(); i++) {
            String barcode = cosmetics.get(i);
            double totalWeight = 0.0;
            double weight = 0.0;
            int n = 0;

            for (int j = 0; j < numSimilarRaters; j++) {
                //CHECK IF THERE IS ANY SIMILARITY, IF NO RETURN EMPTY ARRAYLIST 
                if (raterSimilar.size() == 0) {
                    return ratings;
                }
                String rater_id = "";
                double similarScore = 0.0;
                //TRY CATCH TO PREVENT PUTING HIGH NUMBER OF NumSimilarRaters WHICH EXCEED NUMBER OF RaterSimilar ARRAYLIST
                try {
                    rater_id = raterSimilar.get(j).getItem();
                    similarScore = raterSimilar.get(j).getValue();
                } catch (Exception e) {
                    break;
                }
                double ratingScore = 0.0;
                if (RaterResponsitory.getRater(rater_id).getRating(barcode) != -1) {
                    ratingScore = RaterResponsitory.getRater(rater_id).getRating(barcode);
                    // FORMULAR: WEIGHT = SUM(AVGRATING*SIMILARRATING)/TOTAL NUMBER OF RATERS;
                    weight += similarScore * ratingScore;
                    n++;
                }            
            }
            //WEIGHT ONLY CALCULATE IF THERE IS ENOUGH MININAL NUMBER OF RATERS
            if (n >= minimalRaters) {
                totalWeight = weight/n;
            }
            //ONLY ADD BARCODE THAT HAS MORE THAN 0 SIMILARITY SCORE
            if (totalWeight != 0) {
                ratings.add(new RatingModel(barcode, totalWeight));
            }
        }
        //REVERSE ORDER FROM LARGEST TO SMALLEST SIMILARITYSCORE
        Collections.sort(ratings, Collections.reverseOrder());
        return ratings;
    }
}
