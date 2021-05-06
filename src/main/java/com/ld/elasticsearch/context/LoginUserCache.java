package com.ld.elasticsearch.context;

import com.ld.elasticsearch.entity.mysql.MUser;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginUserCache {
    private static Map<Integer, LoginUser> cache = new HashMap<Integer,LoginUser>();

    /**
     *
     * @param muser
     * @param expire 单位秒，如果设置30分钟过期，即：60*30
     */
    public static void put(MUser muser,long expire){
        long expireTime = Calendar.getInstance().getTimeInMillis() + expire * 1000;
        LoginUser loginUser = new LoginUser();
        loginUser.setExpire(expireTime);
        loginUser.setMuser(muser);
        cache.put(muser.getId(), loginUser);
    }
    public static MUser get(Integer id){
        long expireTime = Calendar.getInstance().getTimeInMillis();
        LoginUser loginUser = cache.get(id);
        if (loginUser.getExpire() - expireTime < 0) {

        }
        return loginUser.getMuser();
    }
    private static class LoginUser{
        private long expire;
        private MUser muser;

        public long getExpire() {
            return expire;
        }

        public void setExpire(long expire) {
            this.expire = expire;
        }

        public MUser getMuser() {
            return muser;
        }

        public void setMuser(MUser muser) {
            this.muser = muser;
        }
    }
}
