
package com.io.bookstores.model.guestModel;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class GuestDataModel {

    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("guestId")
    private Long mGuestId;
    @SerializedName("id")
    private Object mId;
    @SerializedName("modifiedDate")
    private Object mModifiedDate;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private Long mPhone;

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Long getGuestId() {
        return mGuestId;
    }

    public void setGuestId(Long guestId) {
        mGuestId = guestId;
    }

    public Object getId() {
        return mId;
    }

    public void setId(Object id) {
        mId = id;
    }

    public Object getModifiedDate() {
        return mModifiedDate;
    }

    public void setModifiedDate(Object modifiedDate) {
        mModifiedDate = modifiedDate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getPhone() {
        return mPhone;
    }

    public void setPhone(Long phone) {
        mPhone = phone;
    }

}
