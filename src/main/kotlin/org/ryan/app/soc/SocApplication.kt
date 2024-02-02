package org.ryan.app.soc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphdbApplication

fun main(args: Array<String>) {
	runApplication<GraphdbApplication>(*args)
}
