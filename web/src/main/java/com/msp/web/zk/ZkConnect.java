package com.msp.web.zk;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 连接ZK
 *
 * @author maoshiping
 * @create 2017-10-19 18:08
 **/
public class ZkConnect  implements Watcher {
    private final Logger logger = LoggerFactory.getLogger(ZkConnect.class);

    private static CountDownLatch connectedSemaphone=new CountDownLatch(1);
    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper=new ZooKeeper("123.56.223.134:2181",5000,new ZkConnect());
        System.out.println(zooKeeper.getState());
        try {
            connectedSemaphone.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ZooKeeper session established");
        System.out.println("sessionId="+zooKeeper.getSessionId());
        System.out.println("password="+zooKeeper.getSessionPasswd());
    }


    /*
     * 检测是否连接成功
     */
    public void process(WatchedEvent event) {
        System.out.println("my ZookeeperConstructorSimple watcher Receive watched event:"+event);
        if(Event.KeeperState.SyncConnected==event.getState()){
            connectedSemaphone.countDown();
        }
    }
}
