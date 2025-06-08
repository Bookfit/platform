import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration
class JpaConfig {

    @Bean
    fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = dataSource
        emf.setPackagesToScan("com.bookfit.www.backend.domain")  // 엔티티 패키지 위치

        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        emf.jpaVendorAdapter = vendorAdapter

        // 추가 JPA 프로퍼티 (필요시)
        val jpaProperties = HashMap<String, Any>()
        jpaProperties["hibernate.hbm2ddl.auto"] = "update"
        jpaProperties["hibernate.dialect"] = "org.hibernate.dialect.PostgreSQLDialect"
        emf.setJpaPropertyMap(jpaProperties)

        return emf
    }

    @Bean
    fun transactionManager(entityManagerFactory: LocalContainerEntityManagerFactoryBean): JpaTransactionManager {
        return JpaTransactionManager(entityManagerFactory.getObject()!!)
    }
}
