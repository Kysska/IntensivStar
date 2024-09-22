package ru.androidschool.intensiv.data.local.mapper

interface DatabaseMapper<FROM, TO> {

    fun map(from: FROM): TO

    fun map(fromList: Collection<FROM>): List<TO> {
        val result = ArrayList<TO>()
        fromList.mapTo(result) { map(it) }
        return result
    }

    fun reverseMap(to: TO): FROM

    fun reverseMap(toList: Collection<TO>): List<FROM> {
        val result = ArrayList<FROM>()
        toList.mapTo(result) { reverseMap(it) }
        return result
    }
}
