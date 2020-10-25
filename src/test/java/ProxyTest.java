import com.scienjus.Application;
import com.scienjus.Service.UserService;
import com.scienjus.repository.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

public class ProxyTest {
    @Test
    public void proxyT(){
        UserService userService = AspectBeanFactory.getBeanByJDKProxy();
        userService.JdkProxyTest();
    }
    @Test
    public void proxyTCGlib(){
        HashMap hashMap = new HashMap();
        UserDao userDao = AspectBeanFactory.getBeanByCGLIB();
        userDao.select();
    }
}
