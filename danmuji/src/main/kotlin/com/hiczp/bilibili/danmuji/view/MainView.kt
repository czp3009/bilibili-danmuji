package com.hiczp.bilibili.danmuji.view

import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import tornadofx.*


class MainView : View() {
    override val root = BorderPane()
    private val counter = SimpleIntegerProperty()

    init {
        title = "Counter"

        with(root) {
            style {
                padding = box(20.px)
            }

            center {
                vbox(10.0) {
                    alignment = Pos.CENTER

                    label {
                        bind(counter)
                        style { fontSize = 25.px }
                    }

                    button("Click to increment").setOnAction {
                        increment()
                    }
                }
            }
        }
    }

    private fun increment() {
        counter.value++
    }
}
