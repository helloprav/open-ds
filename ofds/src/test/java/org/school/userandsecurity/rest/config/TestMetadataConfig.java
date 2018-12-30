package org.school.userandsecurity.rest.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//import javax.transaction.Transactional;
//import javax.transaction.Transactional.TxType;

import org.junit.runner.RunWith;
import org.openframework.commons.jpa.AbstractTransactionManagementConfig;
import org.openframework.commons.spring.SpringAopConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Good Read:
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/html/integration-testing.html
 * 
 * Good Read for @Transactional
 * http://stackoverflow.com/questions/26387399/javax-transaction-transactional-vs-org-springframework-transaction-annotation-tr
 * 
 * @author pmis30
 *
 */
//@ActiveProfiles("testMysql")
//@ActiveProfiles("testHsql")
//@Transactional(Propagation.NOT_SUPPORTED)//TxType.NOT_SUPPORTED)
//@Transactional ( propagation = Propagation.NOT_SUPPORTED )
@ActiveProfiles("production")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(classes = { TestMetadataConfig.class, SpringAopConfig.class,
		AbstractTransactionManagementConfig.class })
@Configuration
//@Rollback(CookieConstants.ROLLBACK)
@EnableTransactionManagement
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource("classpath:/config/database/jdbc.properties")
@EnableJpaRepositories("org.school.userandsecurity.service.repository")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ComponentScan(basePackages= {"org.school.userandsecurity"})

/**
 * Some Notes of using @Transactional along with @Rollback(boolean)
 * 
 * @Transactional Type Use										ROLLBACK		Service-Result			MVC-Result
 * 
 * NONE															ANY			PASS-Created/Updated	PASS-Created/Updated
 * 
 * org.springframework.transaction.annotation.Transactional		true		PASS-Created/Updated	PASS-Created/Updated
 * org.springframework.transaction.annotation.Transactional		false		PASS-Created/Updated	PASS-Created/Updated

 * javax.transaction.Transactional								true		PASS-Roll Backed		PASS-Created/Updated
 * javax.transaction.Transactional								false		FAIL-Created/Updated	SKIPPED
 * 
 * 
 * 1. If no @Transactional is used, all records are created/updated.
 * 2. If Spring's @Transactional is used, all records are created/updated for any @Rollback
 * 3. If javax's @Transactional is used, created/updated records are roll backed when @Rollback(true).
 * 		a) @Rollback(true) is used on service, and rollback worked.
 * 		b) @Rollback is not used in any controller, rollback didn't happen.
 * 		c) To apply roll back on Controller: http://stackoverflow.com/questions/10019426/spring-transactional-not-working
 * 		d) @Rollback(false) is used, rollback didn't happen.
 *  
 * 
 * 
 * 
 */

public @interface TestMetadataConfig {
}
