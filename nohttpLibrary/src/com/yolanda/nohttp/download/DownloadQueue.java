/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yolanda.nohttp.download;

import java.util.concurrent.LinkedBlockingQueue;

import com.yolanda.nohttp.Logger;

/**
 * <p>Download queue.</p>
 * Created in Oct 21, 2015 2:44:19 PM.
 *
 * @author YOLANDA;
 */
public class DownloadQueue {
    /**
     * Save download task.
     */
    private final LinkedBlockingQueue<NetworkDownloadRequest> mDownloadQueue = new LinkedBlockingQueue<NetworkDownloadRequest>();
    /**
     * Download Network task execution interface.
     */
    private final Downloader mDownloader;
    /**
     * Download queue polling thread array.
     */
    private DownloadDispatcher[] mDispatchers;

    /**
     * Create download queue manager.
     *
     * @param downloader     download the network task execution interface, where you need to implement the download tasks that have been implemented.
     * @param threadPoolSize number of thread pool.
     */
    public DownloadQueue(Downloader downloader, int threadPoolSize) {
        mDownloader = downloader;
        mDispatchers = new DownloadDispatcher[threadPoolSize];
    }

    /**
     * Start polling the download queue, a one of the implementation of the download task, if you have started to poll the download queue, then it will stop all the threads, to re create thread
     * execution.
     */
    public void start() {
        stop();
        for (int i = 0; i < mDispatchers.length; i++) {
            DownloadDispatcher networkDispatcher = new DownloadDispatcher(mDownloadQueue, mDownloader);
            mDispatchers[i] = networkDispatcher;
            networkDispatcher.start();
        }
    }

    /**
     * Add a download task to download queue, waiting for execution, if there is no task in the queue or the number of tasks is less than the number of thread pool, will be executed immediately.
     *
     * @param what             used to distinguish Download.
     * @param downloadRequest  download request object.
     * @param downloadListener download results monitor.
     */
    public void add(int what, DownloadRequest downloadRequest, DownloadListener downloadListener) {
        if (downloadRequest.isQueue())
            Logger.w("This request has been in the queue");
        else {
            downloadRequest.queue(true);
            downloadRequest.start(false);
            downloadRequest.cancel(false);
            downloadRequest.finish(false);
            mDownloadQueue.add(new NetworkDownloadRequest(what, downloadRequest, downloadListener));
        }
    }

    /**
     * Polling the queue will not be executed, and this will not be canceled.
     */
    public void stop() {
        for (DownloadDispatcher mDispatcher : mDispatchers) {
            if (mDispatcher != null)
                mDispatcher.quit();
        }
    }

    /**
     * All requests for the sign specified in the queue, if you are executing, will interrupt the download task.
     *
     * @param sign this sign will be the same as sign's DownloadRequest, and if it is the same, then cancel the task.
     */
    public void cancelBySign(Object sign) {
        synchronized (mDownloadQueue) {
            for (NetworkDownloadRequest networkDownloadRequest : mDownloadQueue)
                networkDownloadRequest.downloadRequest.cancelBySign(sign);
        }
    }

    /**
     * Cancel all requests, Already in the execution of the request can't use this method.
     */
    public void cancelAll() {
        synchronized (mDownloadQueue) {
            for (NetworkDownloadRequest request : mDownloadQueue)
                request.downloadRequest.cancel(true);
        }
    }

}
