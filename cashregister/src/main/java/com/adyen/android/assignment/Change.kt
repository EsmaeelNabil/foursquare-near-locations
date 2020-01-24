package com.adyen.android.assignment

import java.util.TreeMap

class Change {
    private val map by lazy {
        TreeMap<MonetaryElement, Int>(Comparator { lhs, rhs ->
            rhs.minorValue.compareTo(lhs.minorValue)
        })
    }

    val total: Long
        get() = map.entries.sumBy { it.key.minorValue * it.value }.toLong()

    fun put(element: MonetaryElement, count: Int): Change {
        val newCount = (map[element] ?: 0) + count
        if (newCount < 0) {
            throw IllegalArgumentException("Resulting count is less than zero.")
        }
        if (newCount == 0) {
            map.remove(element)
        } else {
            map[element] = newCount
        }
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Change) return false
        return map == other.map
    }

    override fun hashCode(): Int {
        return map.hashCode()
    }

    override fun toString(): String {
        return map.toString()
    }

    companion object {
        fun max(): Change {
            val change = Change()
            Bill.values().forEach { change.put(it, Int.MAX_VALUE) }
            Coin.values().forEach { change.put(it, Int.MAX_VALUE) }
            return change
        }

        fun none(): Change = Change()
    }
}
