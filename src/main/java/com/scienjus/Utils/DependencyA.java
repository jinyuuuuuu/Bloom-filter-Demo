package com.scienjus.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DependencyA {
    public DependencyA() {
        System.out.println("DependencyA被初始化了");
    }

    @Autowired
    private DependencyB b;
     public  void sout(){
         System.out.println("我被调用啦");
     }
}
