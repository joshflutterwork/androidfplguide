package com.fplguide.data.mapper

import com.fplguide.data.remote.dto.BootstrapDto
import com.fplguide.data.remote.dto.ElementDto
import com.fplguide.data.remote.dto.EventDto
import com.fplguide.data.remote.dto.TeamDto
import com.fplguide.domain.model.PlayerStatus
import com.fplguide.domain.model.Position
import org.junit.Assert.assertEquals
import org.junit.Test

class BootstrapMapperTest {

    private val liverpool = TeamDto(id = 1, code = 14, name = "Liverpool", shortName = "LIV")

    private fun element(
        id: Int = 10,
        code: Int = 123456,
        status: String = "a",
        elementType: Int = 3,
        form: String = "8.2",
        pointsPerGame: String = "6.1",
        selectedByPercent: String = "58.3",
        nowCost: Int = 125,
    ) = ElementDto(
        id = id,
        code = code,
        firstName = "Mohamed",
        secondName = "Salah",
        webName = "Salah",
        team = 1,
        elementType = elementType,
        status = status,
        form = form,
        pointsPerGame = pointsPerGame,
        selectedByPercent = selectedByPercent,
        nowCost = nowCost,
        ictIndex = "120.5",
    )

    private fun bootstrap(vararg elements: ElementDto, teams: List<TeamDto> = listOf(liverpool)) =
        BootstrapDto(
            elements = elements.toList(),
            teams = teams,
            events = listOf(
                EventDto(id = 5, name = "Gameweek 5", isCurrent = true),
                EventDto(id = 6, name = "Gameweek 6", isNext = true),
            ),
        )

    @Test
    fun `maps core element fields and joins team`() {
        val snapshot = bootstrap(element()).toDomain()

        val player = snapshot.players.single()
        assertEquals(10, player.id)
        assertEquals("Salah", player.webName)
        assertEquals("Mohamed Salah", player.fullName)
        assertEquals("Liverpool", player.team.name)
        assertEquals(14, player.team.code)
        assertEquals(Position.MIDFIELDER, player.position)
        assertEquals(PlayerStatus.AVAILABLE, player.status)
    }

    @Test
    fun `parses numeric strings from the API`() {
        val player = bootstrap(element()).toDomain().players.single()

        assertEquals(8.2, player.form, 1e-9)
        assertEquals(6.1, player.pointsPerGame, 1e-9)
        assertEquals(58.3, player.selectedByPercent, 1e-9)
        assertEquals(120.5, player.ictIndex, 1e-9)
        assertEquals(125, player.nowCost)
    }

    @Test
    fun `blank and garbage numeric strings degrade to zero`() {
        val player = bootstrap(
            element(form = "", pointsPerGame = "None", selectedByPercent = " "),
        ).toDomain().players.single()

        assertEquals(0.0, player.form, 1e-9)
        assertEquals(0.0, player.pointsPerGame, 1e-9)
        assertEquals(0.0, player.selectedByPercent, 1e-9)
    }

    @Test
    fun `unknown status and element type fall back safely`() {
        val player = bootstrap(element(status = "x", elementType = 99)).toDomain().players.single()

        assertEquals(PlayerStatus.AVAILABLE, player.status)
        assertEquals(Position.MIDFIELDER, player.position)
    }

    @Test
    fun `element with unknown team id keeps a placeholder team`() {
        val player = bootstrap(element(), teams = emptyList()).toDomain().players.single()

        assertEquals("Unknown", player.team.name)
        assertEquals(1, player.team.id)
    }

    @Test
    fun `maps gameweeks and flags current and next`() {
        val gameweeks = bootstrap(element()).toDomain().gameweeks

        assertEquals(2, gameweeks.size)
        assertEquals(true, gameweeks.first { it.id == 5 }.isCurrent)
        assertEquals(true, gameweeks.first { it.id == 6 }.isNext)
    }
}
