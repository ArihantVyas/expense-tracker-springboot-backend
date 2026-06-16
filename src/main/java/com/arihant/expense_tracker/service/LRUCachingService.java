package com.arihant.expense_tracker.service;

import com.arihant.expense_tracker.dto.ConversionRateRequestDto;
import com.arihant.expense_tracker.enums.CacheStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class LRUCachingService {

    private static final Logger logger = LoggerFactory.getLogger(LRUCachingService.class);

    private class CacheNode {
        CacheNode previousNode;
        CacheNode nextNode;
        ConversionRateRequestDto requestDto;
        double exchangeValue;
    }

    private byte nodeCount;
    private final CacheNode dummyHead;
    private final CacheNode dummyTail;
    private final HashMap<ConversionRateRequestDto, CacheNode> currencyCache;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public LRUCachingService() {
        this.nodeCount = 0;
        this.currencyCache = new HashMap<>();
        this.dummyHead = new CacheNode();
        this.dummyTail = new CacheNode();

        this.dummyHead.nextNode = this.dummyTail;
        this.dummyHead.previousNode = null;
        this.dummyTail.previousNode = this.dummyHead;
        this.dummyTail.nextNode = null;

        logger.info("CachingService initialized");

    }

    public void clearCache(){
        // Locking writeLock so that multiple threads cannot modify concurrently and no thread can read while writing operation
        readWriteLock.writeLock().lock();
        logger.info("Write log engaged by thread : "+Thread.currentThread().getName());
        try{
            currencyCache.clear();
            logger.info("Cache cleared");

            this.dummyHead.nextNode = this.dummyTail;
            this.dummyTail.previousNode = this.dummyHead;
            this.nodeCount = 0;
        }finally {
            // Freeing the writeLock
            logger.info("Write Lock freed by thread : "+Thread.currentThread().getName());
            readWriteLock.writeLock().unlock();
        }
    }

    public CacheStatus cacheStatus(ConversionRateRequestDto requestDto){

        // Executing the readLock so many threads can read concurrently but a thread cannot perform write operation during read operation
        readWriteLock.readLock().lock();
        logger.info("Read log engaged by thread : "+Thread.currentThread().getName());

        try{
            if(currencyCache.containsKey(requestDto)){
                logger.info("CACHE_HIT occurred");
                return CacheStatus.CACHE_HIT;
            }
            else{
                logger.info("CACHE_MISS occurred");
                return CacheStatus.CACHE_MISS;
            }
        }
        finally {
            // Freeing the readLock
            logger.info("Read Lock freed by thread : "+Thread.currentThread().getName());
            readWriteLock.readLock().unlock();
        }
    }

    public double getFromCache(ConversionRateRequestDto requestDto){

        /* Even though we are reading data from the cache , it may seem like readLock would be better , since I am
          implementing strict LRU caching by modifying the Doubly LinkedList based on MRU access , so we need to use writeLock to prevent
          corruption of LinkedList or remove any possibility of Dead Lock.
        */
        readWriteLock.writeLock().lock();
        logger.info("Write log engaged by thread : "+Thread.currentThread().getName());
        try{
            if(currencyCache.containsKey(requestDto)){
                // This is the most recently used node
                CacheNode nodeMRU = currencyCache.get(requestDto);

                // Delinking the MRU node from its current positon and linking the MRU's previous and next node
                (nodeMRU.previousNode).nextNode = nodeMRU.nextNode;
                (nodeMRU.nextNode).previousNode = nodeMRU.previousNode;

                // Linking the MRU node before the dummyTail
                nodeMRU.nextNode = this.dummyTail;
                nodeMRU.previousNode = this.dummyTail.previousNode;
                (this.dummyTail.previousNode).nextNode = nodeMRU;
                this.dummyTail.previousNode = nodeMRU;


                return nodeMRU.exchangeValue;
            }
        }finally {
            // Freeing the readLock
            logger.info("Write Lock freed by thread : "+Thread.currentThread().getName());
            readWriteLock.writeLock().unlock();
        }
        return 0;
    }

    public void storeInCache(ConversionRateRequestDto requestDto,double convertedValue){

        // Locking writeLock so that multiple threads cannot modify concurrently and no thread can read while writing operation
        readWriteLock.writeLock().lock();
        logger.info("Write log engaged by thread : "+Thread.currentThread().getName());

        try{
            CacheNode newNode = new CacheNode();
            newNode.exchangeValue = convertedValue;
            newNode.requestDto = requestDto;

            if(nodeCount == 0){
                this.dummyHead.nextNode = newNode;
                this.dummyTail.previousNode = newNode;
                newNode.previousNode = this.dummyHead;
                newNode.nextNode = this.dummyTail;
                this.nodeCount++;
            } else if(nodeCount > 0) {

                if(nodeCount < 10){
                    // Adding the node before the dummyTail , because it is MRU
                    (this.dummyTail.previousNode).nextNode = newNode;
                    newNode.previousNode = this.dummyTail.previousNode;
                    newNode.nextNode = this.dummyTail;
                    this.dummyTail.previousNode = newNode;
                    this.nodeCount++;
                }else if(nodeCount == 10){
                    // Removing the node next to the head , because it is LRU
                    CacheNode nodeToBeRemoved = this.dummyHead.nextNode;
                    this.dummyHead.nextNode = nodeToBeRemoved.nextNode;
                    (nodeToBeRemoved.nextNode).previousNode = this.dummyHead;
                    currencyCache.remove(nodeToBeRemoved.requestDto);
                    this.nodeCount--;

                    // Adding the node before the dummyTail , making it MRU
                    (this.dummyTail.previousNode).nextNode = newNode;
                    newNode.previousNode = this.dummyTail.previousNode;
                    newNode.nextNode = this.dummyTail;
                    this.dummyTail.previousNode = newNode;
                    this.nodeCount++;
                }

            }
            currencyCache.put(requestDto,newNode);
        }finally {
            // Freeing the writeLock
            logger.info("Write Lock freed by thread : "+Thread.currentThread().getName());
            readWriteLock.writeLock().unlock();
        }
    }
}
