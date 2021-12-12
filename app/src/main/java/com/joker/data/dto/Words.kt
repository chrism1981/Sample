package com.joker.data.dto

class Words {
    var counts = 0
    var value = ""
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
    fun countString():String{
        return counts.toString()
    }

    override fun toString(): String {
        return super.toString()
    }
}

