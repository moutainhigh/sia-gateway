/*-
 * <<
 * sag
 * ==
 * Copyright (C) 2019 sia
 * ==
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * >>
 */


package com.creditease.gateway.admin.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.creditease.gateway.admin.service.AdminService;
import com.creditease.gateway.constant.GatewayConstant;

/**
 * 网关管理端初始化
 *
 * @author peihua
 **/
@Component
public class ZuulAdminRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ZuulAdminRunner.class);

    @Autowired
    private AdminService adminservice;

    @Override
    public void run(String... args) throws Exception {

        logger.info("AdminRunner开始初始化");
        registerZuulAdmin();
    }

    public void registerZuulAdmin() throws Exception {

        try {
            boolean rst = adminservice.registerZuulAdmin();

            if (!rst) {
                logger.error("registerZuulAdmin error:{}", rst);
            }
            else {
                logger.info("registerZuulAdmin success:{}", rst);
            }
        }
        catch (DuplicateKeyException e) {

            boolean rst = adminservice.updateZuulAdmin(GatewayConstant.ZuulState.RUNNING);
            if (!rst) {
                logger.error("updateZuulAdmin error:{}", rst);
            }
            else {
                logger.info("> updateZuulAdmin success:{}", rst);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
