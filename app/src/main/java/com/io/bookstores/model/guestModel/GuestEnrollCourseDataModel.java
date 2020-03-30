
package com.io.bookstores.model.guestModel;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class GuestEnrollCourseDataModel {

    @SerializedName("courseId")
    private Long mCourseId;
    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("enrollId")
    private Long mEnrollId;
    @SerializedName("guest")
    private Guest mGuest;
    @SerializedName("guestId")
    private Long mGuestId;
    @SerializedName("isActive")
    private Boolean mIsActive;
    @SerializedName("userId")
    private Object mUserId;

    public Long getCourseId() {
        return mCourseId;
    }

    public void setCourseId(Long courseId) {
        mCourseId = courseId;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public Long getEnrollId() {
        return mEnrollId;
    }

    public void setEnrollId(Long enrollId) {
        mEnrollId = enrollId;
    }

    public Guest getGuest() {
        return mGuest;
    }

    public void setGuest(Guest guest) {
        mGuest = guest;
    }

    public Long getGuestId() {
        return mGuestId;
    }

    public void setGuestId(Long guestId) {
        mGuestId = guestId;
    }

    public Boolean getIsActive() {
        return mIsActive;
    }

    public void setIsActive(Boolean isActive) {
        mIsActive = isActive;
    }

    public Object getUserId() {
        return mUserId;
    }

    public void setUserId(Object userId) {
        mUserId = userId;
    }

}
