package com.articulate.sigma;

import com.articulate.sigma.parsing.PredVarInstTest;
import com.articulate.sigma.parsing.PreprocessorTest;
import com.articulate.sigma.parsing.RowVarTest;
import com.articulate.sigma.parsing.SortalTest;
import com.articulate.sigma.parsing.TypeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    PredVarInstTest.class,
    PreprocessorTest.class,
    RowVarTest.class,
    SortalTest.class,
    TypeTest.class
})
public class IntegrationTestSuite extends IntegrationTestBase {

}
