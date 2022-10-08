package com;

import com.config.SecretKeyUtils;

public class Test {
    public static void main(String[] args){
        SecretKeyUtils.generateKeyPair("pub.key", "pri.key");

    }
}
