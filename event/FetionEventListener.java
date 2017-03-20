package com.feinno.emshop.api.business.event;

import java.util.Map;

/**
 * 飞信事件监听者
 * @author zhujiquan
 * Created by mac on 17/1/19.
 */
public interface FetionEventListener {

    /**
     * 飞信下载事件
     *  Map<String, Object> bundlemap
     *  bundlemap.put("userid", info.getUserID() > 0 ? info.getUserID() : 0);
        bundlemap.put("mobileno", info.getMobileNo() > 0 ? info.getMobileNo() : 0);
        bundlemap.put("bundleid", item.get("id"));
        bundlemap.put("source", source);
        bundlemap.put("ctype", ctype);
     */
    public void pack_download(Map<String, Object> bundlemap) throws  Exception ;

    /**
     * 飞信提交表情包列表事件
     * @throws Exception
     */
    public void submit_installid() throws  Exception ;

    /**
     * 飞信卸载包事件
     * @throws Exception
     */
    public void submit_uninstallid() throws  Exception ;
}
