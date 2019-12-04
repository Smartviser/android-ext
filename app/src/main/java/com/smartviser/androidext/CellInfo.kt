@file:Suppress("unused")

package com.smartviser.androidext

import android.os.Build
import android.telephony.*

val CellInfo.cellId: Long
    get() =
        if (this is CellInfoCdma) {
            this.cellIdentity.basestationId.toLong()
        } else if (this is CellInfoGsm) {
            this.cellIdentity.cid.toLong()
        } else if (this is CellInfoLte) {
            this.cellIdentity.ci.toLong()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && this is CellInfoNr) {
            (this.cellIdentity as CellIdentityNr).nci
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && this is CellInfoTdscdma) {
            this.cellIdentity.cid.toLong()
        } else if (this is CellInfoWcdma) {
            this.cellIdentity.cid.toLong()
        } else {
            0
        }
