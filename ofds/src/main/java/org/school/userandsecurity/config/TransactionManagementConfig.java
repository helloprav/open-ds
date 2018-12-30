package org.school.userandsecurity.config;

import org.openframework.commons.jpa.AbstractTransactionManagementConfig;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@PropertySource("classpath:${env}/config/jdbc.properties")
public class TransactionManagementConfig extends AbstractTransactionManagementConfig {

	private static final String ENTITY_PACKAGE_TO_SCAN = "org.school.userandsecurity.service.entity";
	private static final String HBM2DDL_AUTO = "update";

	static {
		System.out.println("TransactionManagementConfig.static{} ");
	}

	@Override
	public String getEntityPackagesToScan() {
		return ENTITY_PACKAGE_TO_SCAN;
	}

	@Override
	public String getHbm2ddlAuto() {
		return HBM2DDL_AUTO;
	}
}
