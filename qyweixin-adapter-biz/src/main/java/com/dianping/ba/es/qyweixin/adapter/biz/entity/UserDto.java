package com.dianping.ba.es.qyweixin.adapter.biz.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyuchen on 7/22/14.
 */
public class UserDto implements Serializable{
    // loginId 用户唯一标示
    private String userId;
    private Integer loginId;
    private String name;
    private List<Integer> departmentList;
    private String position;
    private String mobile;
    private Integer gender;
    private String tel;
    private String email;
    private String weixinid;
    private String avatar;
    private Integer status;
    private Integer enable;

    private Extattr extattr;

    /** 未冻结 */
    public final static int NORMAL = 1;
    /** 冻结 */
    public final static int FROZEN = 0;

    /** 关注 */
    public final static int FOLLOW = 1;
    /** 冻结 */
    public final static int UNABLE = 1;
    /** 未关注 */
    public final static int UNFOLLOW = 4;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Integer> departmentList) {
        this.departmentList = departmentList;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeixinid() {
        return weixinid;
    }

    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "{userId="+userId+", name="+name+", weixinid="+weixinid+"}";
    }

    public Extattr getExtattr() {
        return extattr;
    }

    public void setExtattr(Extattr extattr) {
        this.extattr = extattr;
    }

    public void setAttr(String name, String value){
        if(extattr==null){
            extattr = new Extattr();
        }
        extattr.getAttrs().add(new Extattr.Attrs(name, value));
    }

    private static class Extattr implements Serializable{
        private List<Attrs> attrs = new ArrayList<Attrs>();

        public List<Attrs> getAttrs() {
            return attrs;
        }

        public void setAttrs(List<Attrs> attrs) {
            this.attrs = attrs;
        }

        private static class Attrs implements Serializable{
            private String name;
            private String value;

            private Attrs(String name, String value) {
                this.name = name;
                this.value = value;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
