package com.everything4droid.kakaoimage.data.util

/**
 * Created by Khajiev Nizomjon on 02/11/2018.
 */
class ErrorKit : Throwable {

    var errorStatus: ERROR_STATUS? = null
        private set
    override var message: String? = null

    constructor(errorStatus: ERROR_STATUS, message: String) {
        this.errorStatus = errorStatus
        this.message = message
    }

    constructor(errorStatus: ERROR_STATUS) {
        this.errorStatus = errorStatus
    }

    constructor(message: String) {
        this.message = message
    }


}

enum class ERROR_STATUS {
    NETWORK,
    INTERNAL_SERVER,
    NOT_FOUND,
    BAD_REQUEST,
    UNKNOWN,
}
