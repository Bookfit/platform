package com.bookfit.www.map.dto.details;

import com.bookfit.www.map.dto.search.CategoryVO;
import com.bookfit.www.map.dto.search.FacilitiesVO;

import java.util.List;

public class MapDetailRequestDto {
    private Long userId;
    private String loginType;
    private String name;
    private List<CategoryVO> categories;
    private String address;
    private String detailAddress;
    private Double lat;
    private Double lon;
    private String weekdayHours;
    private String weekendHours;
    private List<FacilitiesVO> facilities;
    private String description;

    // 기본 생성자
    public MapDetailRequestDto() {
    }

    // Getter / Setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryVO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryVO> categories) {
        this.categories = categories;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getWeekdayHours() {
        return weekdayHours;
    }

    public void setWeekdayHours(String weekdayHours) {
        this.weekdayHours = weekdayHours;
    }

    public String getWeekendHours() {
        return weekendHours;
    }

    public void setWeekendHours(String weekendHours) {
        this.weekendHours = weekendHours;
    }

    public List<FacilitiesVO> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<FacilitiesVO> facilities) {
        this.facilities = facilities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
