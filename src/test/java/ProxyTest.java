import com.scienjus.Application;
import com.scienjus.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

public class ProxyTest {
    @Test
    public void proxyT(){
        UserService userService = AspectBeanFactory.getBean();
        userService.JdkProxyTest();
    }
}
