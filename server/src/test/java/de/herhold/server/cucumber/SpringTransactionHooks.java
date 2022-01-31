package de.herhold.server.cucumber;

import de.herhold.server.repository.ItemRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Slf4j
public class SpringTransactionHooks {//implements BeanFactoryAware {

    @Autowired
    private ItemRepository itemRepository;

    @After(value = "@txn", order = 100)
    public void rollBackTransaction() {
        log.warn("ROllback db");
        itemRepository.deleteAll().subscribe();
    }
//    private BeanFactory beanFactory;
//    private TransactionStatus transactionStatus;
//
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        this.beanFactory = beanFactory;
//    }
//
//    @Before(value = "@txn", order = 100)
//    public void startTransaction() {
//        transactionStatus = obtainPlatformTransactionManager()
//                .getTransaction(new DefaultTransactionDefinition());
//    }
//
//    public PlatformTransactionManager obtainPlatformTransactionManager() {
//        return beanFactory.getBean(PlatformTransactionManager.class);
//    }
//
//    @After(value = "@txn", order = 100)
//    public void rollBackTransaction() {
//        log.warn("ROllback transaction");
//        obtainPlatformTransactionManager()
//                .rollback(transactionStatus);
//    }
}
