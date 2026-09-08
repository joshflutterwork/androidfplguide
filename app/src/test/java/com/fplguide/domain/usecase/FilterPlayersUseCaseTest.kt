package com.fplguide.domain.usecase

import com.fplguide.domain.model.Player
import com.fplguide.domain.model.PlayerQuery
import com.fplguide.domain.model.PlayerSort
import com.fplguide.domain.model.PlayerStatus
import com.fplguide.domain.model.Position
import com.fplguide.domain.model.Team
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FilterPlayersUseCaseTest {

    private val useCase = FilterPlayersUseCase()

    private fun player(
        id: Int,
        webName: String,
        position: Position = Position.MIDFIELDER,
        teamId: Int = 1,
        nowCost: Int = 80,
        minutes: Int = 900,
        status: PlayerStatus = PlayerStatus.AVAILABLE,
        totalPoints: Int = 100,
        form: Double = 5.0,
    ) = Player(
        id = id,
        code = id,
        webName = webName,
        fullName = "Test $webName",
        team = Team(id = teamId, code = teamId, name = "Team $teamId", shortName = "T$teamId"),
        position = position,
        status = status,
        news = "",
        chanceOfPlayingNextRound = null,
        nowCost = nowCost,
        costChangeEvent = 0,
        totalPoints = totalPoints,
        eventPoints = 0,
        pointsPerGame = 0.0,
        form = form,
        selectedByPercent = 0.0,
        minutes = minutes,
        starts = 0,
        goalsScored = 0,
        assists = 0,
        cleanSheets = 0,
        goalsConceded = 0,
        saves = 0,
        bonus = 0,
        bps = 0,
        yellowCards = 0,
        redCards = 0,
        penaltiesOrder = null,
        influence = 0.0,
        creativity = 0.0,
        threat = 0.0,
        ictIndex = 0.0,
        expectedGoals = 0.0,
        expectedAssists = 0.0,
        expectedGoalInvolvements = 0.0,
        expectedGoalsConceded = 0.0,
        expectedGoalsPer90 = 0.0,
        expectedAssistsPer90 = 0.0,
        defensiveContribution = 0,
        transfersInEvent = 0,
        transfersOutEvent = 0,
        valueSeason = 0.0,
    )

    private val players = listOf(
        player(1, "Alpha", position = Position.GOALKEEPER, nowCost = 55, totalPoints = 90, form = 3.0),
        player(2, "Bravo", position = Position.DEFENDER, teamId = 2, nowCost = 65, totalPoints = 120, form = 6.0),
        player(3, "Charlie", position = Position.MIDFIELDER, minutes = 45, totalPoints = 60, form = 2.0),
        player(4, "Delta", position = Position.FORWARD, teamId = 2, status = PlayerStatus.INJURED, totalPoints = 150, form = 7.5),
    )

    @Test
    fun `empty query returns all players sorted by total points descending`() {
        val result = useCase(players, PlayerQuery())

        assertEquals(listOf(4, 2, 1, 3), result.map { it.id })
    }

    @Test
    fun `search matches web name case-insensitively`() {
        val result = useCase(players, PlayerQuery(search = "charlie"))

        assertEquals(listOf(3), result.map { it.id })
    }

    @Test
    fun `search matches team short name`() {
        val result = useCase(players, PlayerQuery(search = "t2"))

        assertEquals(listOf(4, 2), result.map { it.id })
    }

    @Test
    fun `position filter keeps only selected positions`() {
        val result = useCase(
            players,
            PlayerQuery(positions = setOf(Position.DEFENDER, Position.FORWARD)),
        )

        assertEquals(listOf(4, 2), result.map { it.id })
    }

    @Test
    fun `team filter keeps only selected teams`() {
        val result = useCase(players, PlayerQuery(teamIds = setOf(2)))

        assertEquals(listOf(4, 2), result.map { it.id })
    }

    @Test
    fun `max cost filter is inclusive`() {
        val result = useCase(players, PlayerQuery(maxCost = 55, sort = PlayerSort.PRICE_HIGH))

        assertEquals(listOf(1), result.map { it.id })
    }

    @Test
    fun `min minutes filter drops fringe players`() {
        val result = useCase(players, PlayerQuery(minMinutes = 60))

        assertEquals(listOf(4, 2, 1), result.map { it.id })
    }

    @Test
    fun `available only hides risky statuses`() {
        val result = useCase(players, PlayerQuery(availableOnly = true))

        assertEquals(listOf(2, 1, 3), result.map { it.id })
    }

    @Test
    fun `sort by form descending orders by the selector`() {
        val result = useCase(players, PlayerQuery(sort = PlayerSort.FORM))

        assertEquals(listOf(4, 2, 1, 3), result.map { it.id })
    }

    @Test
    fun `ascending order reverses the sort`() {
        val result = useCase(
            players,
            PlayerQuery(sort = PlayerSort.TOTAL_POINTS, descending = false),
        )

        assertEquals(listOf(3, 1, 2, 4), result.map { it.id })
    }

    @Test
    fun `equal sort values break ties by total points then name`() {
        val tied = listOf(
            player(1, "Zed", totalPoints = 50, form = 5.0),
            player(2, "Ada", totalPoints = 100, form = 5.0),
            player(3, "Mid", totalPoints = 75, form = 5.0),
        )
        val result = useCase(tied, PlayerQuery(sort = PlayerSort.FORM))

        assertEquals(listOf(2, 3, 1), result.map { it.id })
    }

    @Test
    fun `combined filters intersect`() {
        val result = useCase(
            players,
            PlayerQuery(
                positions = setOf(Position.FORWARD),
                teamIds = setOf(2),
                availableOnly = true,
            ),
        )

        assertTrue(result.isEmpty())
    }
}
