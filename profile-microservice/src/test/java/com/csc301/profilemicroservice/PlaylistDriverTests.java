package com.csc301.profilemicroservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistDriverTests {

    private PlaylistDriver playlistDriver;

    private static final String TEST_USER_NAME = "testUser";
    private static final String TEST_SONG_ID = "testSongId";

    @Before
    public void setUp() {
        playlistDriver = mock(PlaylistDriver.class);
    }

    @Test
    public void testLikeSong() {
        DbQueryStatus mockStatus = new DbQueryStatus("LIKE_SUCCESS", DbQueryExecResult.QUERY_OK);
        when(playlistDriver.likeSong(TEST_USER_NAME, TEST_SONG_ID)).thenReturn(mockStatus);

        DbQueryStatus result = playlistDriver.likeSong(TEST_USER_NAME, TEST_SONG_ID);
        assertNotNull(result);
        assertEquals("LIKE_SUCCESS", result.getMessage());
        assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
    }

    @Test
    public void testUnlikeSong() {
        DbQueryStatus mockStatus = new DbQueryStatus("UNLIKE_SUCCESS", DbQueryExecResult.QUERY_OK);
        when(playlistDriver.unlikeSong(TEST_USER_NAME, TEST_SONG_ID)).thenReturn(mockStatus);

        DbQueryStatus result = playlistDriver.unlikeSong(TEST_USER_NAME, TEST_SONG_ID);
        assertNotNull(result);
        assertEquals("UNLIKE_SUCCESS", result.getMessage());
        assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
    }

    @Test
    public void testDeleteSongFromDb() {
        DbQueryStatus mockStatus = new DbQueryStatus("DELETE_SUCCESS", DbQueryExecResult.QUERY_OK);
        when(playlistDriver.deleteSongFromDb(TEST_SONG_ID)).thenReturn(mockStatus);

        DbQueryStatus result = playlistDriver.deleteSongFromDb(TEST_SONG_ID);
        assertNotNull(result);
        assertEquals("DELETE_SUCCESS", result.getMessage());
        assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
    }
}

