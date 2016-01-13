package com;

import com.configService.ConfigService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by lodwr on 07.12.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigServiceTest {
    public static final String INPUT = "C:\\Users\\lodwr\\Desktop\\LABdatabases\\sample.csv";

    @Mock
    private Properties properties;

    private ConfigService configService;

    @Before
    public void initMocks() {
        when(properties.getProperty("fileinput")).thenReturn(INPUT);
        configService = new ConfigService(properties);
    }

    @Test
    public void shouldReturnNullForInvalidPropertyName() {
        assertNull(configService.getProperty("non existing"));
    }

    @Test
    public void shouldReturnUrl() {
        assertEquals(INPUT, configService.getProperty("fileinput"));
    }
}