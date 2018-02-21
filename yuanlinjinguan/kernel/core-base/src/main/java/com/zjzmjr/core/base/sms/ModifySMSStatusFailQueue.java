/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.base.sms;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 
 * @author Administrator
 * @version $Id: SendSMSFailQueue.java, v 0.1 2015-11-11 上午11:28:07 Administrator Exp $
 */
public final class ModifySMSStatusFailQueue {
    private static ModifySMSStatusFailQueue storage = new ModifySMSStatusFailQueue();
    private  Queue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
    private ModifySMSStatusFailQueue(){
        
    }
    
    public static ModifySMSStatusFailQueue getSingle(){
        return storage;
    }
    
    public void add(Integer id){
        queue.add(id);
    }
    
    public Integer poll(){
        return queue.poll();
    }
    
    public boolean isEmpty(){
        return queue.isEmpty();
    }
}

