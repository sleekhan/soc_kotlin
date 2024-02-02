package org.ryan.app.soc.ticket.entity


import com.querydsl.core.annotations.QueryEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@QueryEntity
@Table(name = "TICKET")
data class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long?,

    @Column(name = "event_seq")
    private val eventId: Long?,

    @Column(name = "detect_dt", nullable = false)
    private val detected: LocalDateTime?,

    @Column(name = "ticket_no", nullable = false, length = 100)
    private val ticketNo: String?,

    @Column(name = "scenario_name", nullable = false, length = 100)
    private val scenarioName: String?,

    @Column(name = "scenario_desc", nullable = false)
    private val scenarioDesc: String?,

    @Column(name = "src_ip", length = 100)
    private val sourceIp: String?,

    @Column(name = "src_port", length = 100)
    private val sourcePort: String?,

    @Column(name = "nation", length = 100)
    private val nation: String?,

    @Column(name = "nation_code", length = 100)
    private val nationCode: String?,

    @Column(name = "dest_ip", length = 100)
    private val destinationIp: String?,

    @Column(name = "dest_port", length = 100)
    private val destinationPort: String?,

    @Column(name = "protocol", length = 100)
    private val protocol: String?,

    @Column(name = "dest_info")
    private val destinationInfo: String?,

    @Column(name = "count", nullable = false)
    private val count: Int,

    @Column(name = "eqp_name", length = 100)
    private val equipmentName: String?,

    @Column(name = "eqp_action", length = 100)
    private val equipmentAction: String?,

    @Column(name = "event_name", length = 100)
    private val eventName: String?,

    @Column(name = "pattern")
    private val pattern: String?
)