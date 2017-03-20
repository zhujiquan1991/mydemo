package com.feinno.emshop.api.business.event.Listener;

import com.feinno.emshop.api.business.event.FetionEventListener;
import com.feinno.emshop.basicbiz.expression.basicservice.ExpressionInstallService;
import com.feinno.emshop.basicbiz.expression.basicservice.ExpressionPackService;
import com.feinno.emshop.basicbiz.expression.persistence.ExpressionInstallMapper;
import com.feinno.emshop.common.time.DateTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 安装列表监听者
 * Created by mac on 17/1/19.
 */
@Service
public class FetionInstalllistListener implements FetionEventListener{

    @PostConstruct
    public void init(){
        System.out.println("安装监听者初始化");
    }

    @Autowired
    private ExpressionInstallMapper expressionInstallMapper;

    @Autowired
    private ExpressionInstallService expressionInstallService;

    /**
     * bundlemap.put("userid", info.getUserID() > 0 ? info.getUserID() : 0);
       bundlemap.put("mobileno", info.getMobileNo() > 0 ? info.getMobileNo() : 0);
       bundlemap.put("bundleid", item.get("id"));
       bundlemap.put("source", source);
       bundlemap.put("ctype", ctype);
     * @param bundlemap
     * @throws Exception
     */
    @Override
    public void pack_download(Map<String, Object> bundlemap) throws Exception {
        //判断是否只有0，1，如果是则直接插入，否则进入排序业务生成新的
        //判断是否已经有一条，如果有则更新,否则生成一条新的。
        Map<String, Object> query = new HashMap<>();
        query.put("mobileno", bundlemap.get("mobileno"));

        //count=0,1,没有下载或则下载1次的不进入排序逻辑
        int countInstallIds = expressionInstallMapper.getCountInstallIds(query);
        if(countInstallIds > 1){
            //引入排序逻辑
            Map<String,Object> map = expressionInstallService.getSortInstallIds(query);
            //如果不为空，则取出最新的,进行更行操作
            if(map != null){
                //判断是否已经存在该表情包，如果没有则插入最新的
                if(!String.valueOf(map.get("instllids")).contains(bundlemap.get("bundleid").toString())){
                    map.put("instllids",bundlemap.get("bundleid")+","+String.valueOf(map.get("instllids")));
                    expressionInstallMapper.updateInstallIds(map);
                }
            }else{//如果没有，则创建一条排序sortInstallids
                Map<String, Object> item = expressionInstallService.getOneInstall(query.get("mobileno").toString());
                query.clear();
                if (item != null && !item.isEmpty()) {
                    String installidsStr = String.valueOf(item.get("installids"));
                    installidsStr = installidsStr.substring(1,installidsStr.length()-1).replace(" ","");
                    query.put("ids", installidsStr);
                    query.put("lmt", DateTimes.formatDatetime((Date) item.get("updatetime"), DateTimes.PATTERN2));
                    //创建一条sortInstallids，统一设置成pcweb
                    item.put("installids",bundlemap.get("bundleid")+","+installidsStr);
                    item.put("ctype", "pcweb");
                    expressionInstallMapper.saveInstallIds(item);
                }
            }
        }else {//没有表情包，或则只有1个表情包的，不进行排序逻辑，直接插入新的表情包
            bundlemap.put("installids",bundlemap.get("bundleid"));
            bundlemap.remove("bundleid");
            bundlemap.remove("source");
            expressionInstallMapper.saveInstallIds(bundlemap);
        }
    }

    @Override
    public void submit_installid() throws Exception {

    }

    @Override
    public void submit_uninstallid() throws Exception {

    }
}
