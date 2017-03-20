package com.feinno.emshop.api.business.event;

import com.feinno.emshop.api.business.event.Listener.FetionInstalllistListener;
import com.feinno.emshop.basicbiz.expression.basicservice.ExpressionInstallService;
import com.feinno.emshop.basicbiz.expression.persistence.ExpressionInstallMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 飞信监听模式事件启动器
 * Created by mac on 17/1/19.
 * @author zhujiquan
 */
@Component
public class FetionListenerModelInit {

    @PostConstruct
    public void init(){
        //监听模式启动
        FetionEventDispatcher fetionEventDispatcher = new FetionEventDispatcher();
        System.out.println("============飞信事件监听模块初始化成功=======================");
        //加入事务外的日记监听者，主要负责记录日记
//      fetionEventDispatcher.addOuterListener(new FetionLogListener());
//      System.out.println("============日记监听者进入飞信事件监听=======================");
        //加入事务内的安装列表监听者，主要负责安装列表的写入，删除
        fetionEventDispatcher.addInnerListener(new FetionInstalllistListener());
        System.out.println("============安装列表监听者进入飞信事件监听===============");
    }

    @PreDestroy
    public void destory(){

    }
}
