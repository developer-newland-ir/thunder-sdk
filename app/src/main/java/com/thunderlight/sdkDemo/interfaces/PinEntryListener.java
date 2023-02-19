package com.thunderlight.sdkDemo.interfaces;

/**
 * @author youjf
 * @description
 * @date 2019/11/14
 */
public interface PinEntryListener {
    /**
     * input pin finish
     *
     * @param pinblock pinblock data. pinblock is null when input canceled or exception.
     */
    void onFinish(byte[] pinblock);
}