package org.ryan.app.soc.ticket.controller

import mu.KotlinLogging
import org.ryan.app.soc.ticket.dto.TicketDto
import org.ryan.app.soc.ticket.dto.eventName
import org.ryan.app.soc.ticket.service.TicketService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TicketController(val ticketService: TicketService) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/ticket/{ticketId}")
    fun getTicket(@PathVariable("ticketId") id: Long): ResponseEntity<TicketDto> {
        return ticketService.searchTicketById(id).fold(
            { ResponseEntity.notFound().build() },
            { ticketDto -> ResponseEntity.ok(ticketDto) }
        )
    }

    @GetMapping("/ticket/all")
    fun getAllTickets(): List<TicketDto> {
        return ticketService.searchAllTickets()
    }

    @GetMapping("/ticket/test/{ticketId}")
    fun testTicket(@PathVariable("ticketId") id: Long): ResponseEntity<TicketDto> {
        return ticketService.searchTicketById(id).fold(
            { ResponseEntity.notFound().build() },
            { ticketDto ->
                TicketDto.eventName.modify(ticketDto) { "Rollback Test" }
                ticketService.createTicketTest(ticketDto)
                ResponseEntity.ok(ticketDto)
            }
        )
    }
}