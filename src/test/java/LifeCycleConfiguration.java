import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifeCycleConfiguration {
    @Bean(initMethod = "MyBeanInit")
    public ProductTest productTest(){
        return new ProductTest();
    }
}
