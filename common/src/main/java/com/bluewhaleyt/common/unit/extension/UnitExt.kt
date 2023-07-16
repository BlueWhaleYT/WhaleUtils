package com.bluewhaleyt.common.unit.extension

import java.util.Locale

/**
 * Returns a [String] representation of this [Number], formatted to two decimal places.
 *
 * @return the formatted [String] representation of this [Number]
 * @see String.format
 * @see Locale.US
 */
fun Number.formatDecimal(): String {
    return String.format(Locale.US, "%.2f", this)
}