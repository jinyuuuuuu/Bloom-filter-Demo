import com.scienjus.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void sendMessage(){
        //假如要用String来传Message，那么一定要用convertAndSend方法
        rabbitTemplate.convertAndSend("T1","i'm sending message to you");
    }
}
