package com.dianshang.cn.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;


@Configuration
public class ShiroConfig {

	@Value("${spring.redis.shiro.host}")
	private String host;
	
	@Value("${spring.redis.shiro.port}")
	private int port;
	
	@Value("${spring.redis.shiro.timeout}")
	private int timeout;
	
	@Value("${spring.redis.shiro.password}")
	private String password;
	
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//必须设置SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//setloginURL如果不设置值，默认会自动寻找web工程目录下的“/login.jsp”或者“/login”映射
		shiroFilterFactoryBean.setLoginUrl("/index");
		//设置无权限时跳转的url
		shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");;
		
		//设置拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		//游客，开发权限
		filterChainDefinitionMap.put("/guest/**", "anon");
		//用户，user权限
		filterChainDefinitionMap.put("/user/**", "roles[user]");
		//管理员，admin权限
		filterChainDefinitionMap.put("/admin/**", "roles[admin]");
		//开发登录接口 
		//静态资源
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/index", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/html/**", "anon");
		//swagger
		filterChainDefinitionMap.put("/swagger-ui.html","anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/swagger/**","anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**","anon");
		filterChainDefinitionMap.put("/v2/**","anon");
		//其余接口一律拦截
		//主要这行代码必须放在所有权限设置的最后，不然会导致所有url都被拦截
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		System.out.println("shiro拦截器工程注入成功");
		return shiroFilterFactoryBean;
	}
	
	/**
	 * 
	 * 方法描述：   注入securitymanager
	 * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
	 * @Title: securityManager 
	 * @date 2018年8月14日 上午10:59:50
	 * @author wangpan
	 * @modifier 
	 * @modifydate 
	 * @return
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(customRealm());
        return securityManager;
	}
	
	/**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }
    
    /**
     * 
     * 方法描述：   凭证匹配器（由于我们的密码校验交给shrio的SimpleAuthenticationInfo进行处理了所以
     * 我们需要修改下doGetAuthenticationInfo中的代码）
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * @Title: hashedCredentialsMatcher 
     * @date 2018年8月14日 下午4:13:46
     * @author wangpan
     * @modifier 
     * @modifydate 
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher  hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//使用MD5散列算法
        hashedCredentialsMatcher.setHashIterations(1);//散列次数，这里等于1次MD5
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);  //散列后密码为16进制，要与生成密码时一致。false 表示Base64编码
        return hashedCredentialsMatcher;
    }
    
    /**
     * cookie对象;
     * 记住密码实现起来也是比较简单的，主要看下是如何实现的。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }
    
    /**
     * 
     * 方法描述：   自定义sessionManager
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * @Title: sessionManager 
     * @date 2018年8月15日 上午10:17:39
     * @author wangpan
     * @modifier 
     * @modifydate 
     * @return
     */
//    @Bean
//    public SessionManager sessionManager() {
//    	MySessionManager mySessionManager = new MySessionManager();
//    	mySessionManager.setSessionDAO(redisSessionDao());
//    	return mySessionManager;
//    }
    
    /**
     * 
     * 方法描述：   配置shiro redismanager
     * 使用shiro-redis插件
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * @Title: redisManager 
     * @date 2018年8月15日 上午11:04:09
     * @author wangpan
     * @modifier 
     * @modifydate 
     * @return
     */
    /*public RedisManager redisManager() {
    	RedisManager redisManager = new RedisManager();
    	redisManager.setHost(host);
    	redisManager.setPort(port);
    	redisManager.setTimeout(timeout);
    	redisManager.setPassword(password);
    	return redisManager;
    }*/
    
    /**
     * 
     * 方法描述：   cacheManager 缓存 redis实现
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * @Title: cacheManager 
     * @date 2018年8月15日 上午11:06:51
     * @author wangpan
     * @modifier 
     * @modifydate 
     * @return
     */
//    @Bean
//    public RedisCacheManager cacheManager() {
//    	RedisCacheManager redisCacheManager = new RedisCacheManager();
//    	redisCacheManager.setExpire(1800);//配置缓存过期时间
//    	redisCacheManager.setRedisManager(redisManager());
//    	return redisCacheManager;
//    }
    
    /**
     * 
     * 方法描述：   redisSessionDao shiro sessionDao 层的实现
     * 通过redis
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * @Title: redisSessionDao 
     * @date 2018年8月15日 上午11:09:19
     * @author wangpan
     * @modifier 
     * @modifydate 
     * @return
     */
//    @Bean
//    public RedisSessionDAO redisSessionDao() {
//    	RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//    	redisSessionDAO.setRedisManager(redisManager());
//    	return redisSessionDAO;
//    }
    
    /**
     * 
     * 方法描述：   开启shiro aop注解支持
     * 使用代理的方式，所以需要开启代码支持
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * @Title: authorizationAttributeSourceAdvisor 
     * @date 2018年8月15日 上午11:11:29
     * @author wangpan
     * @modifier 
     * @modifydate 
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
    	AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    	advisor.setSecurityManager(securityManager);
    	return advisor;
    }

    /**
     * 
     * 方法描述：   全局异常处理
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * @Title: handlerExceptionResolver 
     * @date 2018年8月15日 上午10:07:12
     * @author wangpan
     * @modifier 
     * @modifydate 
     * @return
     */
//    @Bean
//    public HandlerExceptionResolver handlerExceptionResolver() {
//    	return new myExceptionHandler();
//    }
}
