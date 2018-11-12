//package com.local.test;
//
//import io.choerodon.devops.api.controller.v1.DevopsIngressController;
//import io.choerodon.devops.api.dto.DevopsIngressDTO;
//import io.choerodon.devops.api.dto.DevopsIngressPathDTO;
//import io.choerodon.devops.app.service.DevopsIngressService;
//import org.junit.Test;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//public class TestAddIngress {
//
//    @Test
//    public  void testIngress(){
//        DevopsIngressController controller = new DevopsIngressController();
//        DevopsIngressDTO devopsIngressDTO = new DevopsIngressDTO();
//        devopsIngressDTO.setDomain("abc");
//        devopsIngressDTO.setName("miehaha");
//        devopsIngressDTO.setEnvId(15L);
//
//        DevopsIngressPathDTO dip = new DevopsIngressPathDTO();
//        dip.setPath("/abc2-r-aaa");
//        dip.setRewritePath("/");
//        devopsIngressDTO.setPathList(Collections.singletonList(dip));
//
//        DevopsIngressService devopsIngressService = new DevopsIngressService();
//
//
//        controller.create(1L,  devopsIngressDTO);
//    }
//
//
//}
