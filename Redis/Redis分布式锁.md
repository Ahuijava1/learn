```java
/**
 * 尝试获取 redis 分布式锁
 * 
 * @param lockKey
 *            redis 锁key
 * @param tryTimeoutMills
 *            获取锁的超时时间
 * @param lockDurationMills
 *            锁定时长
 * @return 锁过期时间戳
 */
public long tryLock(String lockKey, long tryTimeoutMills, long lockDurationMills) {
   try {
      lockDurationMills = lockDurationMills <= 0 ? DEFAULT_LOCK_DURATION_MILLS : lockDurationMills;
      Long current = System.currentTimeMillis();
      Long expireTime = current + lockDurationMills;
      do {
          // setnx，不存在则设置，成功返回1
         Integer i = redisClient.setnx(lockKey, Long.toString(expireTime));
         // 获取到锁
         if (i == 1) {
            if (logger.isDebugEnabled()) {
               logger.debug("{} tryLock succ. lockKey: {}, tryTimeoutMills: {}, lockDurationMills: {}",
                     logPrefix, lockKey, tryTimeoutMills, lockDurationMills);
            }
            redisClient.expire(lockKey, (int) (lockDurationMills / 1000));
            return expireTime;
         }

         // 若获取不到锁，则检查锁的有效时长，以防止expire失败时造成永远都取不到锁
         String val = redisClient.get(lockKey);
         if (StringUtils.isNotBlank(val)) {
            if ((System.currentTimeMillis() - NumberUtils.toLong(val)) > lockDurationMills) {
               redisClient.del(lockKey);
               logger.info(
                     "{} over max lock duration, del key: {}, tryTimeoutMills: {}, lockDurationMills: {}",
                     logPrefix, lockKey, tryTimeoutMills, lockDurationMills);
            }
         }

         // 防止空转抢占CPU资源
         if (tryTimeoutMills > 0) {
            Thread.sleep(5 + random.nextInt(10));
         }
      } while ((System.currentTimeMillis() - current) < tryTimeoutMills);

   } catch (Exception e) {
      logger.error(logPrefix + " tryLock fail. lockKey: " + lockKey + ", tryTimeoutMills: " + tryTimeoutMills
            + ", lockDurationMills: " + lockDurationMills, e);
   }
   return 0;
}
```

