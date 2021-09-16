package util

import kotlinx.browser.window

val isPortrait = window.matchMedia("(orientation: portrait)").matches
val isLandscape = window.matchMedia("(orientation: landscape)").matches