package ru.androidschool.intensiv.domain

import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.domain.entity.CastCard

interface CastRepository {
    fun getCasts(id: Int, callback: (CustomResult<List<CastCard>>) -> Unit)
}
