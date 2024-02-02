package org.ryan.app.soc.ticket.dto

import arrow.optics.optics
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

@optics data class TicketDto @QueryProjection constructor(
    val id: Long?,
    val eventId: Long?,
    val detected: LocalDateTime?,
    val ticketNo: String?,
    val scenarioName: String?,
    val scenarioDesc: String?,
    val sourceIp: String?,
    val sourcePort: String?,
    val nation: String?,
    val nationCode: String?,
    val destinationIp: String?,
    val destinationPort: String?,
    val protocol: String?,
    val destinationInfo: String?,
    val count: Int,
    val equipmentName: String?,
    val equipmentAction: String?,
    val eventName: String?,
    val pattern: String?
) {
    companion object
}