package com.tbse.wnsw.ui.aplist.preview

import kotlin.random.Random.Default.nextInt

/**
 * Created by toddsmith on 5/16/21.
 * Copyright TBSE 2022
 */
class RandomString {
    fun random(): String = List(nextInt(6, 20)) {
        (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
    }.joinToString("")
}