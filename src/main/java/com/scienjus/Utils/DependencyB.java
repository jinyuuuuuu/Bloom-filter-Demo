package com.scienjus.Utils;

import com.scienjus.Utils.DependencyA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DependencyB {
    @Autowired
    private DependencyA a;
}
