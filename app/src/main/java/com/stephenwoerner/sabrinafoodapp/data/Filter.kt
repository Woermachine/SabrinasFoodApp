package com.stephenwoerner.sabrinafoodapp.data

enum class Action {
    Include,
    Exclude,
    Optional,
}

class Filter(val action: Action, val tag: Tag) {
    val toPredicate: (Restaurant) -> Boolean = {
        when (action) {
            Action.Include -> { it.tags.contains(tag) }
            Action.Exclude -> { !it.tags.contains(tag) }
            Action.Optional -> { it.tags.contains(tag) }
        }
    }
}