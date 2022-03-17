package com.esmaeel.challenge.utils

/**
 * making sure we are handling any exception that's been thrown
 */
class UnhandledTestException(errorMessage: String) : Throwable(message = errorMessage)