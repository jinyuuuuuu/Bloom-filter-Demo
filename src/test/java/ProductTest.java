import com.scienjus.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ResolvableType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductTest implements BeanFactoryAware ,BeanNameAware {

    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ProductTest() {
        Name = "2";
        System.out.println("构造器被初始化");
    }

    /**
     * 设置Bean的名称
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        setName(name);
        System.out.println("我是BeanName:"+Name);
    }

    public void MyBeanInit(){
        System.out.println("我是Bean的初始化方法");
    }

    /**
     * 传入当前Bean工厂实例的引用
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("我是BeanFactory："+beanFactory.getClass());
    }

    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    /**
     * 直接模式
     */
    @Test
    public void sendMessageDirect(){
        //假如要用String来传Message，那么一定要用convertAndSend方法
        rabbitTemplate.convertAndSend("T1","i'm sending message to you");
    }

    /**
     * 分裂模式
     */
    @Test
    public void sendMessageFanout(){
        rabbitTemplate.convertAndSend("fanoutModeExchange","","i'm sending message through fanoutMode(QueueName)");
    }

    /**
     * 主题模式
     */
    @Test
    public void sendMessageTopic(){
        rabbitTemplate.convertAndSend("topicModeExchange","good.log","i'm sending message through topic mode");
    }
}
