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

import com.yolanda.nohttp.Headers;

/**
 * <p>The download process monitor.</p>
 * Created in Jul 31, 2015 9:12:55 AM;
 *
 * @author YOLANDA;
 */
public interface DownloadListener {

    /**
     * An error occurred while downloading.
     *
     * @param what      which is used to mark the download tasks.
     * @param exception error.
     */
    void onDownloadError(int what, Exception exception);

    /**
     * When this download task starts the callback method.
     *
     * @param what            which is used to mark the download tasks.
     * @param isResume        whether to continue to download, if it is true that has download before, and have already.
     *                        download the file size is not zero.
     * @param rangeSize       hTTP starting point size, the size of the data already exists.
     * @param responseHeaders server response headers.
     * @param allCount        total file size.
     */
    void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount);

    /**
     * When the download process change.
     *
     * @param what      which is used to mark the download tasks.
     * @param progress  this method is the time to change the progress of the download.
     * @param fileCount have downloaded the file size.
     */
    void onProgress(int what, int progress, long fileCount);

    /**
     * Download is complete.
     *
     * @param what     which is used to mark the download tasks.
     * @param filePath where is the file after the download is complete.
     */
    void onFinish(int what, String filePath);

    /**
     * Download request is canceled.
     *
     * @param what which is used to mark the download tasks.
     */
    void onCancel(int what);
}