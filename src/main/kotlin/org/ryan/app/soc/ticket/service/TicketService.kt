package org.ryan.app.soc.ticket.service

import arrow.core.Option
import org.ryan.app.soc.ticket.dto.TicketDto
import org.ryan.app.soc.ticket.repository.TicketRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketService(val ticketRepository: TicketRepository) {
    fun searchTicketById(id: Long): Option<TicketDto> {
        return ticketRepository.findTicketById(id)
    }

    fun searchAllTickets(): List<TicketDto> {
        return ticketRepository.findAllTickets()
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun createTicket(ticketDto: TicketDto) {
        ticketRepository.save(ticketDto).fold(
            { exception -> throw exception },
            { Unit }
        )
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun createTicketTest(ticketDto: TicketDto) {
        ticketRepository.save(ticketDto).fold(
            { exception -> throw exception },
            { Unit }
        )
        throw Exception("RollbackTest")
    }

}