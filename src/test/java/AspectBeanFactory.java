import com.scienjus.Service.UserService;
import com.scienjus.Service.UserServiceImp;
import com.scienjus.repository.UserDao;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AspectBeanFactory {
    public static UserService getBeanByJDKProxy(){
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

    public static UserDao getBeanByCGLIB(){
        final UserDao userDao = new UserDao();
        final MyAspect myAspect = new MyAspect();
        //核心类
        Enhancer enhancer = new Enhancer();
        //需要增强的类，也就只CGlib创建的子类的父类
        enhancer.setSuperclass(userDao.getClass());
        //这是回调函数
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                myAspect.myBefore();
                Object obj = method.invoke(userDao,args);
                myAspect.myAfter();
                return  obj;
            }
        });
        UserDao userDao1Cglib = (UserDao)enhancer.create();
        return userDao1Cglib;
    }
}
