import com.scienjus.Application;
import com.scienjus.Utils.DependencyA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BeanLifeTest implements BeanNameAware,BeanFactoryAware,ApplicationContextAware,InitializingBean,DisposableBean {
    private String MyName;

    public BeanLifeTest() {
        System.out.println("构造器Name："+MyName);
    }
    //通过Setter方法来注入Bean
    public void setMyName(String myName) {
        System.out.println("属性填充");
        MyName = myName;
    }
   /* //通过构造函数来注入Bean
    public BeanLifeTest(String myName) {
        MyName = myName;
    }*/

    /**
     * 通过实现BeanNameAware接口来实现BeanName的注入
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        System.out.println("BeanName:"+name);
    }

    /**
     * 实现BeanFactoryAware接口来实现传入当前的BeanFactory的实现类
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactory:"+beanFactory.getClass());
    }

    /**
     *传入当前ApplicationContext的实现类
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicaitonContextAware的setApplicationContext方法被调用"+applicationContext.getClass());
    }

    /**
     * 在这之前BeanPostHandle先执行了PreInitialize的预初始化方法，然后才实现BeanPostProcessor接口来实现afterPropertiesSet()方法
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("这个是InitializingBean的初始化Bean方法");
    }

    /**
     * 在@Configuration下自己定义的Bean初始化方法,相对于上面的那个方法，这样就对Spring解耦合了
     */
    public void MyInitMethod(){
        System.out.println("Bean的自定义初始化方法实现");
    }
/**
 * 这里还会实现一个BeanPostHandler的PostInitialize方法，后初始化方法
 */
    /**
     * 实现Disposabel接口来实现的Bean销毁方法
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("Disposable的Bean销毁方法");
    }

    /**
     * 通过@Configuration下自定定义的Bean销毁方法，相对于上面的那个方法，这样就对Spring解耦合了
     */
    public void MyDestroyMethod(){
        System.out.println("自定义Bean销毁方法");
    }
    @Autowired
    private DependencyA dependencyA;
    @Test
    public void test(){
        ApplicationContext context = new AnnotationConfigApplicationContext(LifeCycleConfiguration.class);
        ((AnnotationConfigApplicationContext) context).close();
        dependencyA.sout();
    }
}
