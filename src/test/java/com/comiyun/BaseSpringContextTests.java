package com.comiyun;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager="transactionManager",defaultRollback = false)
@ContextConfiguration(locations = { "classpath:app-context.xml","classpath:app-mvc.xml"})
public class BaseSpringContextTests extends AbstractTransactionalJUnit4SpringContextTests {

}
