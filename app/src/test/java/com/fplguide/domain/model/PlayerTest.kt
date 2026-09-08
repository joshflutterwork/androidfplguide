package com.fplguide.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PlayerTest {

    private fun player(
        nowCost: Int = 125,
        totalPoints: Int = 200,
        transfersInEvent: Int = 100_000,
        transfersOutEvent: Int = 40_000,
        code: Int = 123456,
    ) = Player(
        id = 1,
        code = code,
        webName = "Salah",
        fullName = "Mohamed Salah",
        team = Team(id = 1, code = 14, name = "Liverpool", shortName = "LIV"),
        position = Position.MIDFIELDER,
        status = PlayerStatus.AVAILABLE,
        news = "",
        chanceOfPlayingNextRound = null,
        nowCost = nowCost,
        costChangeEvent = 0,
        totalPoints = totalPoints,
        eventPoints = 0,
        pointsPerGame = 0.0,
        form = 0.0,
        selectedByPercent = 0.0,
        minutes = 0,
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
        transfersInEvent = transfersInEvent,
        transfersOutEvent = transfersOutEvent,
        valueSeason = 0.0,
    )

    @Test
    fun `price label formats tenths of millions`() {
        assertEquals("£12.5m", player(nowCost = 125).priceLabel)
        assertEquals("£4.0m", player(nowCost = 40).priceLabel)
        assertEquals("£15.1m", player(nowCost = 151).priceLabel)
    }

    @Test
    fun `value per million divides points by price in millions`() {
        assertEquals(16.0, player(nowCost = 125, totalPoints = 200).valuePerMillion, 1e-9)
    }

    @Test
    fun `value per million guards against zero cost`() {
        assertEquals(0.0, player(nowCost = 0).valuePerMillion, 1e-9)
    }

    @Test
    fun `net transfers is inbound minus outbound`() {
        assertEquals(60_000, player().netTransfersEvent)
    }

    @Test
    fun `goal involvements sums goals and assists`() {
        val p = player().copy(goalsScored = 7, assists = 5)

        assertEquals(12, p.goalInvolvements)
    }

    @Test
    fun `photo url is keyed by code not id`() {
        assertEquals(
            "https://resources.premierleague.com/premierleague/photos/players/250x250/p123456.png",
            player(code = 123456).photoUrl,
        )
    }

    @Test
    fun `status code mapping covers known codes and falls back`() {
        assertEquals(PlayerStatus.INJURED, PlayerStatus.fromCode("i"))
        assertEquals(PlayerStatus.SUSPENDED, PlayerStatus.fromCode("s"))
        assertEquals(PlayerStatus.AVAILABLE, PlayerStatus.fromCode("a"))
        assertEquals(PlayerStatus.AVAILABLE, PlayerStatus.fromCode("zzz"))

        assertTrue(PlayerStatus.INJURED.isRisky)
        assertFalse(PlayerStatus.AVAILABLE.isRisky)
    }

    @Test
    fun `position mapping covers element types and falls back to midfielder`() {
        assertEquals(Position.GOALKEEPER, Position.fromElementType(1))
        assertEquals(Position.DEFENDER, Position.fromElementType(2))
        assertEquals(Position.MIDFIELDER, Position.fromElementType(3))
        assertEquals(Position.FORWARD, Position.fromElementType(4))
        assertEquals(Position.MIDFIELDER, Position.fromElementType(0))
    }
}

class PlayerQueryTest {

    @Test
    fun `default query has no active filters`() {
        val query = PlayerQuery()

        assertEquals(0, query.activeFilterCount)
        assertFalse(query.hasActiveFilters)
    }

    @Test
    fun `filter count is capped per filter group`() {
        val query = PlayerQuery(
            positions = setOf(Position.GOALKEEPER, Position.FORWARD),
            teamIds = setOf(1, 2, 3),
            maxCost = 100,
            minMinutes = 45,
            availableOnly = true,
        )

        // positions count as one group, teams as one group, then the three toggles
        assertEquals(5, query.activeFilterCount)
        assertTrue(query.hasActiveFilters)
    }
}
