/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */
package com.octo.captcha.engine.bufferedengine.manager;

import org.apache.commons.collections.map.HashedMap;

/**
 * Interface that adds manageability options to the BufferedEngineContainer
 *
 * @author Benoit Doumas
 */
public interface BufferedEngineContainerManager {

    /**
     * Get number of captchas to feed the disk buffer
     */
    int getFeedSize();

    /**
     * @param feedSize Set number of captchas to produce at each iteration.
     */
    public void setFeedSize(int feedSize);

    /**
     * @param size Set number of captchas to swap between the volatil buffer and the disk buffer
     */
    void setSwapSize(int size);

    /**
     * Get number of captchas to swap between the volatil buffer and the disk buffer
     */
    int getSwapSize();

    /**
     * @param size Set maximun size for the volatile buffer
     */
    void setMaxVolatileMemorySize(int size);

    /**
     * Get maximun size for the volatile buffer
     */
    int getMaxVolatileMemorySize();

    /**
     * @param size Set maximum size for the disk buffer
     */
    void setMaxPersistentMemorySize(int size);

    /**
     * Get maximum size for the disk buffer
     */
    int getMaxPersistentMemorySize();

    /**
     * @param localeName Name of th locale to set or to create
     * @param ratio      The ratio of the locale
     */
    void setLocaleRatio(String localeName, double ratio);

    /**
     * @return the number of volatile memory access
     */
    int getVolatileMemoryHits();

    /**
     * @return the number of persistent accesses
     */
    int getPersistentMemoryHits();

    /**
     * @return the number of disk feedings
     */
    int getPersistentFeedings();

    /**
     * @return the number of swap from disk to memory
     */
    int getPersistentToVolatileSwaps();

    /**
     * @return the size of the volatile Buffer (RAM buffer)
     */
    int getVolatileBufferSize();

    /**
     * @return an hashedMap of sizes by locale
     */
    HashedMap getVolatileBufferSizeByLocales();

    /**
     * @return the size of the persitent Buffer (disk buffer)
     */
    int getPersistentBufferSize();

    /**
     * @return an hashedMap of sizes by locale
     */
    HashedMap getPersistentBufferSizesByLocales();

    /**
     * Tell the scheduler to start to feed the persistent buffer
     */
    void startToFeedPersistantBuffer();

    /**
     * Tell the scheduler to stop to feed the persistent buffer
     */
    void stopToFeedPersistentBuffer();

    /**
     * Tell the scheduler to start to swap captchas from persistent buffer to memory buffer
     */
    void startToSwapFromPersistentToVolatileMemory();

    /**
     * Tell the scheduler to stop to swap captchas from persistent buffer to memory buffer
     */
    void stopToSwapFromPersistentToVolatileMemory();

    /**
     * Clear the volatile buffer
     */
    void clearVolatileBuffer();

    /**
     * Clear the persitent buffer
     */
    void clearPersistentBuffer();

    /**
     * Pause the scheduler, both the swapping and the feeding process are paused
     */
    void pause();

    /**
     * Resume the scheduler, both the swapping and the feeding process are resumed
     */
    abstract void resume();

    /**
     * Shutdown scheduling, the container will use its memory buffer until its empty and swtich to the engine.
     */
    void shutdown();
}
