@file:Suppress("unused")

package com.smartviser.androidext

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.telecom.PhoneAccountHandle
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager

@TargetApi(Build.VERSION_CODES.M)
fun subscriptionInfoForPhoneAccount(context: Context, phoneAccount: PhoneAccountHandle): SubscriptionInfo? {
    if (!context.checkPermission(android.Manifest.permission.READ_PHONE_STATE)) {
        return null
    }

    val subscriptionManager =
        context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

    return subscriptionManager.activeSubscriptionInfoList?.let { infoList ->
        infoList.find {
            it.iccId.startsWith(phoneAccount.id) || phoneAccount.id.contains(it.iccId)
        } ?: infoList.find { phoneAccount.id == it.subscriptionId.toString() }
    }
}

@TargetApi(Build.VERSION_CODES.M)
fun phoneAccountHandleAt(context: Context, slotIndex: Int): PhoneAccountHandle? {
    var phoneAccountHandle: PhoneAccountHandle? = null
    if (!context.checkPermission(android.Manifest.permission.READ_PHONE_STATE)) {
        return null
    }

    val telecomManager = context.telecomManager
    val subscriptionManager = context.subscriptionManager
    val subscriptionInfo = subscriptionManager.activeSubscriptionInfoList?.find {
        it.simSlotIndex == slotIndex
    }

    val accounts = telecomManager.callCapablePhoneAccounts
    if (subscriptionInfo != null && subscriptionInfo.iccId.isNotEmpty()) {
        phoneAccountHandle = accounts.find {
            subscriptionInfo.iccId.startsWith(it.id) || it.id.contains(subscriptionInfo.iccId)
        } ?: accounts.find { it.id == subscriptionInfo.subscriptionId.toString() }
    }

    return phoneAccountHandle
}
