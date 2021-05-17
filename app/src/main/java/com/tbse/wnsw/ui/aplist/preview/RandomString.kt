package com.tbse.wnsw.ui.aplist.preview

import kotlin.random.Random.Default.nextInt

/**
 * Created by toddsmith on 5/16/21.
 * Copyright TBSE 2017
 */
class RandomString {
    fun random(): String = List(nextInt(12)) {
        (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
    }.joinToString("")
}