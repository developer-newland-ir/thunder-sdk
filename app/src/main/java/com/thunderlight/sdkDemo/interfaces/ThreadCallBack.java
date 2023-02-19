package com.thunderlight.sdkDemo.interfaces;

/**
 * the thread runs the callback
 *
 * @author CB
 * @date 2014/12/25
 */
public interface ThreadCallBack {

    /**
     * executing in the background does not block the ui
     */
    void onBackGround();

    /**
     * when executed on the main thread it blocks the ui
     */
    void onMain();
}
