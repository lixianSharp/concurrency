package com.mmall.concurrency.example.lock;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@ThreadSafe
public class LockExample3 {

    private final Map<String,Data> map = new TreeMap<String, Data>();

    //创建一个读写锁
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    //获取读锁
    private final Lock readLock = lock.readLock();
    //获取写锁
    private final Lock writeLock = lock.writeLock();

    public Data get(String key){
        readLock.lock();//加读锁
        try {
            return map.get(key);
        } finally {
            readLock.unlock();//释放读锁
        }
    }

    public Set<String> getAllKeys(){
        readLock.lock();//加读锁
        try {
            return map.keySet();
        } finally {
            readLock.unlock();//释放读锁
        }

    }

    public Data put(String key,Data value){
        writeLock.lock();//加写锁
        try {
            return map.put(key,value);
        } finally {
            readLock.unlock();//释放读锁
        }
    }

}
