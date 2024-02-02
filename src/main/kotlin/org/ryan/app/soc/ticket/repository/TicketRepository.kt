package org.ryan.app.soc.ticket.repository


import arrow.core.*
import com.querydsl.jpa.impl.JPAQueryFactory
import mu.KotlinLogging
import org.ryan.app.soc.common.convertTo
import org.ryan.app.soc.common.transform
import org.ryan.app.soc.ticket.dto.QTicketDto
import org.ryan.app.soc.ticket.dto.TicketDto
import org.ryan.app.soc.ticket.entity.QTicket.ticket
import org.ryan.app.soc.ticket.entity.Ticket
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface TicketJpaRepository : JpaRepository<Ticket, Long> {}

@Repository
class TicketRepository(val queryFactory: JPAQueryFactory) {
    private val logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var ticketJpaRepository: TicketJpaRepository

    @Transactional
    fun save(ticketDto: TicketDto): Either<Exception, Any> {
        return try {
            val ticket = ticketDto.transform { it.convertTo<TicketDto, Ticket>() }
            ticketJpaRepository.save(ticket).right()
        } catch (e: Exception) {
            e.left()
        }
    }

    fun findAllTickets(): List<TicketDto> {
        return queryFactory.select(
            QTicketDto(
                ticket.id,
                ticket.eventId,
                ticket.detected,
                ticket.ticketNo,
                ticket.scenarioName,
                ticket.scenarioDesc,
                ticket.sourceIp,
                ticket.sourcePort,
                ticket.nation,
                ticket.nationCode,
                ticket.destinationIp,
                ticket.destinationPort,
                ticket.protocol,
                ticket.destinationInfo,
                ticket.count,
                ticket.equipmentName,
                ticket.equipmentAction,
                ticket.eventName,
                ticket.pattern
            )
        )
            .from(ticket)
            .fetch()
    }

    fun findTicketById(id: Long): Option<TicketDto> {
        return queryFactory.select(
            QTicketDto(
                ticket.id,
                ticket.eventId,
                ticket.detected,
                ticket.ticketNo,
                ticket.scenarioName,
                ticket.scenarioDesc,
                ticket.sourceIp,
                ticket.sourcePort,
                ticket.nation,
                ticket.nationCode,
                ticket.destinationIp,
                ticket.destinationPort,
                ticket.protocol,
                ticket.destinationInfo,
                ticket.count,
                ticket.equipmentName,
                ticket.equipmentAction,
                ticket.eventName,
                ticket.pattern
            )
        )
            .from(ticket)
            .where(ticket.id.eq(id))
            .fetchOne()
            .toOption()
    }
}
