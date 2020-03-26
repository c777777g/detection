package com.commonsl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;


/**
 * 加密工具栏
 *
 * @author Jan
 */

public class PropertyPlaceholderConfigurerExt extends PropertyPlaceholderConfigurer {

    private final static Logger log = LoggerFactory.getLogger(PropertyPlaceholderConfigurerExt.class);

    protected void processProperties(
            ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {


        String projectSwitch = props.getProperty("project.switch");
        log.info("======PropertyPlaceholderConfigurerExt ==== : {}" + projectSwitch);

        String password = null, url = null, username = null;

        url = props.getProperty("jdbc.url");
        username = props.getProperty("jdbc.username");
        password = props.getProperty("jdbc.password");


        try {
            if (password != null) {

                props.setProperty("jdbc.password", password);// 赋值
            }
            if (url != null) {
                props.setProperty("jdbc.url", url);// 赋值
            }
            if (username != null) {
                props.setProperty("jdbc.username", username);// 赋值
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.processProperties(beanFactory, props);
    }

}

