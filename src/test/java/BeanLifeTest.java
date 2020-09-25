import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanLifeTest implements BeanNameAware,BeanFactoryAware,ApplicationContextAware,InitializingBean,DisposableBean {
    private String MyName;

    public BeanLifeTest() {
        System.out.println("构造器Name："+MyName);
    }

    public void setMyName(String myName) {
        System.out.println("属性填充");
        MyName = myName;
    }

    /**
     * 通过实现BeanNameAware接口来实现BeanName的注入
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        System.out.println("BeanName:"+name);
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactory:"+beanFactory.getClass());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicaitonContextAware的setApplicationContext方法被调用");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("WTF？这个于下面那个是一样的？");
    }
    public void MyInitMethod(){
        System.out.println("Bean的自定义初始化方法实现");
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("WTF？这个于上面那个是一样的？");
    }
    public void MyDestroyMethod(){
        System.out.println("Bean销毁了");
    }


    @Test
    public void test(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LifeCycleConfiguration.class);
        context.getBean("beanLifeTest");
        context.close();
    }
}
