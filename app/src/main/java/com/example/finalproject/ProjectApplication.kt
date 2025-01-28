package com.example.finalproject

import android.app.Application
import com.example.finalproject.dependeciesinjection.AppContainer
import com.example.finalproject.dependeciesinjection.ProjectContainer

class ProjectApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = ProjectContainer()
    }
}