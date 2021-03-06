package com.io.bookstore.model.courseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnrollCourseDataModel {
    @SerializedName("courseId")
    @Expose
    private Integer courseId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("enrollId")
    @Expose
    private Integer enrollId;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getEnrollId() {
        return enrollId;
    }

    public void setEnrollId(Integer enrollId) {
        this.enrollId = enrollId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
