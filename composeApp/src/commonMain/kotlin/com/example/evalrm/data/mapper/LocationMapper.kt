package com.example.evalrm.data.mapper

import com.example.evalrm.data.remote.model.LocationRemote
import com.example.evalrm.domain.model.Location

fun LocationRemote.toDomain(): Location = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents,
    url = url,
    created = created,
)

