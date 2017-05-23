package com.sky.tools.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/22.
 */

public class ArrayUtilsTest {
    @Test
    public void getLatestEntryBeforeTargetValueFromEnd(){
        int[] test = {1, 2, 3, 4, 5};
        int value = ArrayUtils.getLatestEntryBeforeTargetValueFromEnd(test, 4, 0, false);
        Assert.assertEquals(3, value);
    }
}
