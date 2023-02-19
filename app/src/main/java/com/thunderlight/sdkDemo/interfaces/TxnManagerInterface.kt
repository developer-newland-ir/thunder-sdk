package com.thunderlight.sadad.interfaces

/**
 * Socket monitor
 *
 * @author jianshengd
 * @date 2018/2/19
 */
interface TxnManagerInterface {
    fun afterSendData()
    fun receivedMessage(response: ByteArray?)
}