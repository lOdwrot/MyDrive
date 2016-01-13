package com.drive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static com.drive.Validator.pointDirecory;

import static org.junit.Assert.*;

/**
 * Created by lodwr on 07.12.2015.
 */
public class ValidatorTest {
    public static final String LOCALIZATION = "D:\\ListenIt";
    public static final String EXISTED_FILE="D:\\Linux\\linuxmint-17.1-cinnamon-64bit.iso";
    public static final String UNEXISTED_LOACALIZTION="D:\\Linux\\aaa";


    @Test(expected = NullPointerException.class)
    public void shouldCheckCorrectionForNull(){
        pointDirecory(null);
    }

    @Test
    public void sholdReturnTrueForCorrectDirectory(){
        assertTrue(pointDirecory("D:\\"));
    }
    @Test
    public void sholdReturnFalseForUnexistedDir(){
        assertFalse(pointDirecory(EXISTED_FILE));
    }
    @Test
    public void sholdReturnFalseForFileDirectory(){
        assertFalse(Validator.pointDirecory(UNEXISTED_LOACALIZTION));
    }

}