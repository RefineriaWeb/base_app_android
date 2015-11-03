package com.refineriaweb.app.presentation;

import com.refineriaweb.app.presentation.demo.GetUserTestRefineriaWeb;
import com.refineriaweb.app.presentation.demo.GetUserTestJakeWharton;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetUserTestRefineriaWeb.class,
        GetUserTestJakeWharton.class
})

public class SuiteIntegrationTest {
    
}
