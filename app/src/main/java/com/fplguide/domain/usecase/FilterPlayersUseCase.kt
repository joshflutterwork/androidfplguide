package com.fplguide.domain.usecase

import com.fplguide.domain.model.Player
import com.fplguide.domain.model.PlayerQuery
import javax.inject.Inject

/**
 * Applies [PlayerQuery] to an already-loaded player list.
 *
 * `bootstrap-static` hands us the whole league (~650 players) in one payload, so filtering
 * and sorting stay in memory instead of hitting the network per keystroke.
 */
class FilterPlayersUseCase @Inject constructor() {

    operator fun invoke(players: List<Player>, query: PlayerQuery): List<Player> {
        val term = query.search.trim()
        val filtered = players.filter { player ->
            matchesSearch(player, term) &&
                (query.positions.isEmpty() || player.position in query.positions) &&
                (query.teamIds.isEmpty() || player.team.id in query.teamIds) &&
                (query.maxCost == null || player.nowCost <= query.maxCost) &&
                player.minutes >= query.minMinutes &&
                (!query.availableOnly || !player.status.isRisky)
        }

        val comparator = compareBy<Player> { query.sort.selector(it) }
            // Stable tiebreak so equal-valued rows keep a predictable order between recompositions.
            .thenBy { it.totalPoints }
            .thenBy { it.webName }

        return if (query.descending) filtered.sortedWith(comparator.reversed()) else filtered.sortedWith(comparator)
    }

    private fun matchesSearch(player: Player, term: String): Boolean {
        if (term.isEmpty()) return true
        return player.webName.contains(term, ignoreCase = true) ||
            player.fullName.contains(term, ignoreCase = true) ||
            player.team.name.contains(term, ignoreCase = true) ||
            player.team.shortName.equals(term, ignoreCase = true)
    }
}
