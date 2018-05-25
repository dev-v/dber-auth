package com.dber.auth;


import com.dber.auth.api.AuthApiConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AuthApiConfig.class})
public class AuthApiApplicationTests {

  private static final Log log = LogFactory.getLog(AuthApiApplicationTests.class);

  @Test
  public void contextLoads() {
    log.info("*********************************************************************************************************************");
    log.info("*********************************************************************************************************************");

  }
}
