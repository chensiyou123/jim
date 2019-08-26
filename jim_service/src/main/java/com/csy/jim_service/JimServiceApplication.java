package com.csy.jim_service;

import com.csy.jim_service.command.DemoWsHandshakeProcessor;
import com.csy.jim_service.listener.ImDemoGroupListener;
import com.csy.jim_service.service.LoginServiceProcessor;
import com.csy.jim_service.utils.PropUtil;
import org.apache.commons.lang3.StringUtils;
import org.jim.common.ImConfig;
import org.jim.common.ImConst;
import org.jim.common.config.PropertyImConfigBuilder;
import org.jim.common.packets.Command;
import org.jim.server.ImServerStarter;
import org.jim.server.command.CommandManager;
import org.jim.server.command.handler.HandshakeReqHandler;
import org.jim.server.command.handler.LoginReqHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tio.core.ssl.SslConfig;

@SpringBootApplication
public class JimServiceApplication {

    public static void main(String[] args) throws Exception {
        ImConfig imConfig = new PropertyImConfigBuilder("jim.properties").build();
        //初始化SSL;(开启SSL之前,你要保证你有SSL证书哦...)
        initSsl(imConfig);
        //设置群组监听器，
        imConfig.setImGroupListener(new ImDemoGroupListener());
        ImServerStarter imServerStarter = new ImServerStarter(imConfig);
        /*****************start 以下处理器根据业务需要自行添加与扩展，每个Command都可以添加扩展,此处为demo中处理**********************************/
        HandshakeReqHandler handshakeReqHandler = CommandManager.getCommand(Command.COMMAND_HANDSHAKE_REQ, HandshakeReqHandler.class);
        //添加自定义握手处理器;
        handshakeReqHandler.addProcessor(new DemoWsHandshakeProcessor());
        LoginReqHandler loginReqHandler = CommandManager.getCommand(Command.COMMAND_LOGIN_REQ,LoginReqHandler.class);
        loginReqHandler.addProcessor(new LoginServiceProcessor());
        /*****************end *******************************************************************************************/
        SpringApplication.run(JimServiceApplication.class, args);
    }


    /**
     * 开启SSL之前，你要保证你有SSL证书哦！
     * @param imConfig:配置文件{
     * 	                ssl：off：关闭；on:开启
     * 	  }
     * @throws Exception
     */
    private static void initSsl(ImConfig imConfig) throws Exception {
        //开启SSL
        if(ImConst.ON.equals(imConfig.getIsSSL())){
            String keyStorePath = PropUtil.get("jim.key.store.path");
            String keyStoreFile = keyStorePath;
            String trustStoreFile = keyStorePath;
            String keyStorePwd = PropUtil.get("jim.key.store.pwd");
            if (StringUtils.isNotBlank(keyStoreFile) && StringUtils.isNotBlank(trustStoreFile)) {
                SslConfig sslConfig = SslConfig.forServer(keyStoreFile, trustStoreFile, keyStorePwd);
                imConfig.setSslConfig(sslConfig);
            }
        }
    }

}
