package com.feinno.emshop.api.business.event.Listener;

import com.feinno.emshop.api.business.event.FetionEventListener;

import java.util.Map;

/**
 * 日记监听者
 * Created by mac on 17/1/19.
 */
public class FetionLogListener implements FetionEventListener{


    /**
     *
     * bundlemap.put("userid", info.getUserID() > 0 ? info.getUserID() : 0);
     bundlemap.put("mobileno", info.getMobileNo() > 0 ? info.getMobileNo() : 0);
     bundlemap.put("bundleid", item.get("id"));
     bundlemap.put("source", source);
     bundlemap.put("ctype", ctype);
     */
    @Override
    public void pack_download(Map<String, Object> bundlemap) throws Exception {

    }

    @Override
    public void submit_installid() throws Exception {

    }

    @Override
    public void submit_uninstallid() throws Exception {

    }
}
