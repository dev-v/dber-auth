package com.dber.auth.config;

import com.dber.auth.resource.config.JwtAuthenticationResourceConfig;
import com.dber.platform.config.PlatformWebServiceConfig;
import com.dber.platform.mybatis.plugin.pagination.PaginationInterceptor;
import com.dber.platform.util.DBUtil;
import com.dber.tool.config.SecurityConfig;
import com.dber.tool.config.ServerConfig;
import com.dber.tool.config.SystemConfig;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * <li>文件名称: AuthService.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月21日
 */

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableConfigurationProperties({SystemConfig.class, ServerConfig.class, SecurityConfig.class})
@Import({PlatformWebServiceConfig.class, JwtAuthenticationResourceConfig.class})
@MapperScan(basePackages = {"com.dber.auth.mapper"})
@ComponentScan("com.dber.auth")
public class AuthServiceConfig {
  @Autowired
  private SystemConfig systemConfig;

  @Bean
  @Primary
  public DataSource authDataSource() {
    DataSource authDataSource = DBUtil.dataSource(systemConfig.getDatabase());
    return authDataSource;
  }

  @Bean
  public DataSourceTransactionManager authTransactionManager() {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(authDataSource());
    return transactionManager;
  }

  @Bean
  public org.apache.ibatis.session.Configuration authMybatisConfiguration() {
    org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
    configuration.setMapUnderscoreToCamelCase(true);
    configuration.getTypeAliasRegistry().registerAliases("com.dber.auth.api.entity");
    return configuration;
  }

  @Bean
  public SqlSessionFactoryBean authSqlSessionFactoryBean() throws IOException {
    SqlSessionFactoryBean authSqlSessionFactoryBean = new SqlSessionFactoryBean();

    authSqlSessionFactoryBean.setDataSource(authDataSource());

    authSqlSessionFactoryBean.setConfiguration(authMybatisConfiguration());

    PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    authSqlSessionFactoryBean
            .setMapperLocations(resourceResolver.getResources("classpath*:/mapper/*_mapper.xml"));

    Interceptor[] interceptors = {PaginationInterceptor.getInstance()};
    authSqlSessionFactoryBean.setPlugins(interceptors);

    return authSqlSessionFactoryBean;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
