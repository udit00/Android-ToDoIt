package com.udit.todoit.api

import com.udit.todoit.api.volley.VolleyImpl
import com.udit.todoit.entry_point.application.MyApp
import javax.inject.Inject


class Api @Inject constructor(private val myApp: MyApp): VolleyImpl(myApp) {}