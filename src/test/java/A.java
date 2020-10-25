import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Server;

public class A extends AbstractA implements InterfaceA {


    @Override
    public void A() {

    }

    @Override
    public void whoareU() {

    }

    @Override
    public void whoAmI() {
        super.whoAmI();
    }
}
