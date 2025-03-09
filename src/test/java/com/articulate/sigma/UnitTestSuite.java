package com.articulate.sigma;

import com.articulate.sigma.parsing.SUOKIFCacheTest;
import com.articulate.sigma.parsing.SUOKIFparseTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    SUOKIFCacheTest.class,
    SUOKIFparseTest.class
})
public class UnitTestSuite {

}
