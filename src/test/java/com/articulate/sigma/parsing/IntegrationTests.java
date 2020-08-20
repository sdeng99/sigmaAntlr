package com.articulate.sigma.parsing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PredVarInstTest.class,
        RowVarTest.class,
        SortalTest.class,
        TypeTest.class,
        PreprocessorTest.class,
})
public class IntegrationTests {
}
