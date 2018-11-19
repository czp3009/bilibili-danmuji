package com.hiczp.bilibili.danmuji

import com.hiczp.bilibili.danmuji.view.MainView
import tornadofx.App
import tornadofx.launch

class Application : App() {
    override val primaryView = MainView::class
}

fun main(args: Array<String>) {
    launch<Application>(args)
}
