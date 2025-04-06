package com.cosmeticrecommend.models;

public class CosmeticModel implements Comparable<CosmeticModel>{

	private String barcode;
	private String imageLink;
	private String productName;
	private String category;
	private String brand;
	private double price;
	private String ingredients;
	private String skinConditions;
	private String addProperties;
	private double ratingScore;
	private String raterID;
	private double raterScore;
	private double closeScore;
	
	public CosmeticModel() {}
	
	public CosmeticModel(String barcode, String imageLink, String productName, String category, String brand,
			double price, String ingredients, String skinConditions, String addProperties, double ratingScore,
			String raterID, double raterScore) {
		super();
		this.barcode = barcode;
		this.imageLink = imageLink;
		this.productName = productName;
		this.category = category;
		this.brand = brand;
		this.price = price;
		this.ingredients = ingredients;
		this.skinConditions = skinConditions;
		this.addProperties = addProperties;
		this.ratingScore = ratingScore;
		this.raterID = raterID;
		this.raterScore = raterScore;
	}
	
	public CosmeticModel(String barcode, String imageLink, String productName, String category, String brand,
			double price, String ingredients, double ratingScore, double closeScore) {
		super();
		this.barcode = barcode;
		this.imageLink = imageLink;
		this.productName = productName;
		this.category = category;
		this.brand = brand;
		this.price = price;
		this.ingredients = ingredients;
		this.ratingScore = ratingScore;
		this.closeScore = closeScore;
	}
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public String getSkinConditions() {
		return skinConditions;
	}
	public void setSkinConditions(String skinConditions) {
		this.skinConditions = skinConditions;
	}
	public String getAddProperties() {
		return addProperties;
	}
	public void setAddProperties(String addProperties) {
		this.addProperties = addProperties;
	}
	public double getRatingScore() {
		return ratingScore;
	}
	public void setRatingScore(double ratingScore) {
		this.ratingScore = ratingScore;
	}

	public String getRaterID() {
		return raterID;
	}

	public void setRaterID(String raterID) {
		this.raterID = raterID;
	}

	public double getRaterScore() {
		return raterScore;
	}

	public void setRaterScore(double raterScore) {
		this.raterScore = raterScore;
	}
	
	public double getCloseScore() {
		return closeScore;
	}
	public void setCloseScore(double closeScore) {
		this.closeScore = closeScore;
	}

	@Override
	public int compareTo(CosmeticModel o) {
		// TODO Auto-generated method stub
		return this.category.compareTo(o.category);
	}
	
	public boolean isCloseScoreExist() {
		return this.closeScore > 0;
	}

	
}
