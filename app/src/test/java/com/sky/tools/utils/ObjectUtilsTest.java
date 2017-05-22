package com.sky.tools.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/22.
 */

public class ObjectUtilsTest {
    @Test
    public void isEqual(){
        Assert.assertTrue(ObjectUtils.isEquals(null, null));
    }
}
