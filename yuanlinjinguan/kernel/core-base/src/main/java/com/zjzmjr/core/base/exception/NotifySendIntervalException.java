package com.zjzmjr.core.base.exception;

/**
 * 
 * 
 * @author liwen
 * @version $Id: NotifySendIntervalException.java, v 0.1 2015-12-31 上午11:37:10 liwen Exp $
 */
public class NotifySendIntervalException extends RuntimeException {
    private static final long serialVersionUID = 8346710032239389079L;
    private long interval;
    
    public NotifySendIntervalException(long interval){
        super();
        this.interval = interval;
    }
    public NotifySendIntervalException(String msg,long interval){
        super(msg);
        this.interval = interval;
    }
    public NotifySendIntervalException(String msg,Throwable e,long interval){
        super(msg, e);
        this.interval = interval;
    }
    public long getInterval() {
        return interval;
    }
    public void setInterval(long interval) {
        this.interval = interval;
    }
}
