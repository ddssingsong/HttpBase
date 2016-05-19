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
package com.yolanda.nohttp;

/**
 * Created in Jan 29, 2016 10:56:37 AM.
 *
 * @author YOLANDA;
 */
public interface OnUploadListener extends ProgressHandler {

    /**
     * At the start of the upload is invoked.
     *
     * @param what what of {@link FileBinary#setUploadListener(int, OnUploadListener)}.
     * @see FileBinary#setUploadListener(int, OnUploadListener)
     */
    void onStart(int what);

    /**
     * Called when the upload was cancelled.
     *
     * @param what what of {@link FileBinary#setUploadListener(int, OnUploadListener)}.
     * @see FileBinary#setUploadListener(int, OnUploadListener)
     */
    void onCancel(int what);

    /**
     * Upload is complete is invoked.
     *
     * @param what what of {@link FileBinary#setUploadListener(int, OnUploadListener)}.
     * @see FileBinary#setUploadListener(int, OnUploadListener)
     */
    void onFinish(int what);

    /**
     * Upload error is called.
     *
     * @param what      what of {@link FileBinary#setUploadListener(int, OnUploadListener)}.     * @param exception upload is called when an error occurs.
     * @param exception may be {@link com.yolanda.nohttp.error.NotFoundFileError}, {@link java.io.IOException}
     * @see FileBinary#setUploadListener(int, OnUploadListener)
     */
    void onError(int what, Exception exception);
}
