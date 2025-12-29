package com.mtv.based.core.network.model

object StatusCode {

    const val RC_SUCCESS = "00"

    /* 10xx — Request & Validation */
    const val RC_BAD_REQUEST = "1000"
    const val RC_VALIDATION_ERROR = "1001"
    const val RC_MISSING_PARAMETER = "1002"
    const val RC_INVALID_FORMAT = "1003"
    const val RC_INVALID_VALUE = "1004"

    /* 20xx — Authentication */
    const val RC_UNAUTHORIZED = "2000"
    const val RC_TOKEN_EXPIRED = "2001"
    const val RC_INVALID_TOKEN = "2002"
    const val RC_TOKEN_REQUIRED = "2003"

    /* 30xx — Authorization */
    const val RC_FORBIDDEN = "3000"
    const val RC_ACCESS_DENIED = "3001"

    /* 40xx — Business / Conflict */
    const val RC_CONFLICT = "4000"
    const val RC_DUPLICATE_DATA = "4001"
    const val RC_INVALID_STATE = "4002"
    const val RC_DATA_NOT_FOUND = "4003"

    /* 50xx — Server */
    const val RC_INTERNAL_ERROR = "5000"
    const val RC_SERVICE_UNAVAILABLE = "5001"
    const val RC_BAD_GATEWAY = "5002"
    const val RC_GATEWAY_TIMEOUT = "5003"

    /* 90xx — System / Infrastructure */
    const val RC_TIMEOUT = "9000"
    const val RC_NETWORK_ERROR = "9001"
    const val RC_DEPENDENCY_FAILURE = "9002"

}