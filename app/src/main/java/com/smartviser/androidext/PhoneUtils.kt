@file:Suppress("unused")

package com.smartviser.androidext

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telecom.PhoneAccountHandle
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import androidx.core.content.ContextCompat

@TargetApi(Build.VERSION_CODES.M)
fun subscriptionInfoForPhoneAccount(
    context: Context,
    phoneAccount: PhoneAccountHandle
): SubscriptionInfo? {
    if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_PHONE_STATE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return null
    }

    val subscriptionManager =
        context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
    val infoList = subscriptionManager.activeSubscriptionInfoList

    if (infoList != null) {
        for (subscriptionInfo in infoList) {
            if (subscriptionInfo.iccId.startsWith(phoneAccount.id) || phoneAccount.id.contains(
                    subscriptionInfo.iccId
                )
            ) {
                return subscriptionInfo
            }
        }

        for (subscriptionInfo in infoList) {
            if (phoneAccount.id == Integer.toString(subscriptionInfo.subscriptionId)) {
                return subscriptionInfo
            }
        }
    }

    return null
}
