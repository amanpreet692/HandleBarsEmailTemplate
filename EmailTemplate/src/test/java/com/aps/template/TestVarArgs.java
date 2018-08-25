package com.aps.template;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by APS on 19-08-2018.
 */
public class TestVarArgs {


    @Test
    public void testVarArgs() {
        varArgs(null);
    }

    @Test
    public void testForIndex(){
        for(int i =0; i<10;) {
            print(i);
        }
    }

    private void print (int i){
        System.out.println(i);
        i++;
    }
    private void varArgs(Integer... ints) {
        System.out.println(Arrays.asList(ints));
    }

}
