import com.scienjus.Service.UserService;
import com.scienjus.Service.UserServiceImp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AspectBeanFactory {
    public static UserService getBean(){
        final UserService userService = new UserServiceImp();
        final MyAspect myAspect = new MyAspect();
        //第一个参数为当前类的类加载器
        //第二个参数为要创建实力的接口类
        //第三个参数是需要增强的方法
        return (UserService)Proxy.newProxyInstance(UserService.class.getClassLoader(),
                new Class[]{UserService.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        myAspect.myBefore();
                        Object o = method.invoke(userService,args);
                        myAspect.myAfter();
                        return o;
                    }
                });
    }
}
