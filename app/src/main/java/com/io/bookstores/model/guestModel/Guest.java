
package com.io.bookstores.model.guestModel;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Guest {

    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("guestId")
    private Long mGuestId;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;

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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

}
