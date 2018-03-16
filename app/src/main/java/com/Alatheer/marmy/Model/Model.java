package com.Alatheer.marmy.Model;

import com.google.gson.annotations.SerializedName;

public class Model{

	@SerializedName("playground_cost")
	private String playgroundCost;

	@SerializedName("playground_info")
	private String playgroundInfo;

	@SerializedName("playground_google_lat")
    private Double playgroundGoogleLat;

	@SerializedName("user_id_fk")
	private String userIdFk;

	@SerializedName("playground_governorate_id_fk")
	private String playgroundGovernorateIdFk;

	@SerializedName("playground_city_id_fk")
	private String playgroundCityIdFk;

	@SerializedName("image_name")
	private String imageName;

	@SerializedName("playground_evaluate")
	private String playgroundEvaluate;

	@SerializedName("date_s")
	private String dateS;

	@SerializedName("playground_name")
	private String playgroundName;

	@SerializedName("playground_google_lng")
    private Double playgroundGoogleLng;

	@SerializedName("playground_id_pk")
	private String playgroundIdPk;

	@SerializedName("playground_address")
	private String playgroundAddress;

	@SerializedName("playground_descrip")
	private String playgroundDescrip;

	@SerializedName("playground_capacity")
	private String playgroundCapacity;

	public String getPlaygroundCost(){
		return playgroundCost;
	}

    public void setPlaygroundCost(String playgroundCost) {
        this.playgroundCost = playgroundCost;
    }

	public String getPlaygroundInfo(){
		return playgroundInfo;
	}

    public void setPlaygroundInfo(String playgroundInfo) {
        this.playgroundInfo = playgroundInfo;
    }

    public Double getPlaygroundGoogleLat() {
        return playgroundGoogleLat;
	}

    public void setPlaygroundGoogleLat(Double playgroundGoogleLat) {
        this.playgroundGoogleLat = playgroundGoogleLat;
    }

	public String getUserIdFk(){
		return userIdFk;
	}

    public void setUserIdFk(String userIdFk) {
        this.userIdFk = userIdFk;
    }

	public String getPlaygroundGovernorateIdFk(){
		return playgroundGovernorateIdFk;
	}

    public void setPlaygroundGovernorateIdFk(String playgroundGovernorateIdFk) {
        this.playgroundGovernorateIdFk = playgroundGovernorateIdFk;
    }

	public String getPlaygroundCityIdFk(){
		return playgroundCityIdFk;
	}

    public void setPlaygroundCityIdFk(String playgroundCityIdFk) {
        this.playgroundCityIdFk = playgroundCityIdFk;
    }

	public String getImageName(){
		return imageName;
	}

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

	public String getPlaygroundEvaluate(){
		return playgroundEvaluate;
	}

    public void setPlaygroundEvaluate(String playgroundEvaluate) {
        this.playgroundEvaluate = playgroundEvaluate;
    }

	public String getDateS(){
		return dateS;
	}

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

	public String getPlaygroundName(){
		return playgroundName;
	}

    public void setPlaygroundName(String playgroundName) {
        this.playgroundName = playgroundName;
    }

    public Double getPlaygroundGoogleLng() {
        return playgroundGoogleLng;
	}

    public void setPlaygroundGoogleLng(Double playgroundGoogleLng) {
        this.playgroundGoogleLng = playgroundGoogleLng;
    }

	public String getPlaygroundIdPk(){
		return playgroundIdPk;
	}

    public void setPlaygroundIdPk(String playgroundIdPk) {
        this.playgroundIdPk = playgroundIdPk;
    }

	public String getPlaygroundAddress(){
		return playgroundAddress;
	}

    public void setPlaygroundAddress(String playgroundAddress) {
        this.playgroundAddress = playgroundAddress;
    }

	public String getPlaygroundDescrip(){
		return playgroundDescrip;
	}

    public void setPlaygroundDescrip(String playgroundDescrip) {
        this.playgroundDescrip = playgroundDescrip;
    }

	public String getPlaygroundCapacity(){
		return playgroundCapacity;
	}

    public void setPlaygroundCapacity(String playgroundCapacity) {
        this.playgroundCapacity = playgroundCapacity;
    }

	@Override
 	public String toString(){
        return
                "Model{" +
                        "playground_cost = '" + playgroundCost + '\'' +
                        ",playground_info = '" + playgroundInfo + '\'' +
                        ",user_id_fk = '" + userIdFk + '\'' +
                        ",playground_governorate_id_fk = '" + playgroundGovernorateIdFk + '\'' +
                        ",playground_city_id_fk = '" + playgroundCityIdFk + '\'' +
                        ",image_name = '" + imageName + '\'' +
                        ",playground_evaluate = '" + playgroundEvaluate + '\'' +
                        ",date_s = '" + dateS + '\'' +
                        ",playground_name = '" + playgroundName + '\'' +
                        ",playground_id_pk = '" + playgroundIdPk + '\'' +
                        ",playground_address = '" + playgroundAddress + '\'' +
                        ",playground_descrip = '" + playgroundDescrip + '\'' +
                        ",playground_capacity = '" + playgroundCapacity + '\'' +
                        "}";
		}

}