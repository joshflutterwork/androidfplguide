package com.fplguide.data.remote.api

import com.fplguide.data.remote.dto.BootstrapDto
import com.fplguide.data.remote.dto.ElementSummaryDto
import com.fplguide.data.remote.dto.FixtureDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * The public (unauthenticated) Fantasy Premier League endpoints.
 * Base URL: https://fantasy.premierleague.com/api/
 *
 * Note the trailing slashes — the FPL backend 301-redirects requests without them.
 */
interface FplApi {

    @GET("bootstrap-static/")
    suspend fun getBootstrap(): BootstrapDto

    @GET("element-summary/{playerId}/")
    suspend fun getElementSummary(@Path("playerId") playerId: Int): ElementSummaryDto

    /** Whole-season fixture list (~380 rows); filter by `event` client-side. */
    @GET("fixtures/")
    suspend fun getFixtures(): List<FixtureDto>

    companion object {
        const val BASE_URL = "https://fantasy.premierleague.com/api/"
    }
}
