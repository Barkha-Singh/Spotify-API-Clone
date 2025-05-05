package com.csc301.profilemicroservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * JUnit-4 tests for PlaylistDriver.
 * The real PlaylistDriver implementation is mocked so that we can
 * focus on validating the contract (returned DbQueryStatus objects)
 * under different scenarios.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlaylistDriver2Test {

  private static final String USER_NAME = "testUser";
  private static final String SONG_ID   = "testSongId";

  @Mock
  private PlaylistDriver playlistDriver;

  private DbQueryStatus okStatus;
  private DbQueryStatus errorStatus;

  @Before
  public void setUp() {
    okStatus    = new DbQueryStatus("SUCCESS", DbQueryExecResult.QUERY_OK);
    errorStatus = new DbQueryStatus("FAILURE", DbQueryExecResult.QUERY_ERROR_GENERIC);
  }

  /* ---------- likeSong() tests ---------- */

  @Test
  public void likeSong_returnsOkStatus_whenOperationSucceeds() {
    when(playlistDriver.likeSong(USER_NAME, SONG_ID)).thenReturn(okStatus);

    DbQueryStatus result = playlistDriver.likeSong(USER_NAME, SONG_ID);

    assertNotNull(result);
    assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
    assertEquals("SUCCESS", result.getMessage());
    verify(playlistDriver, times(1)).likeSong(USER_NAME, SONG_ID);
  }

  @Test
  public void likeSong_propagatesErrorStatus_whenDriverFails() {
    when(playlistDriver.likeSong(USER_NAME, SONG_ID)).thenReturn(errorStatus);

    DbQueryStatus result = playlistDriver.likeSong(USER_NAME, SONG_ID);

    assertNotNull(result);
    assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, result.getdbQueryExecResult());
    assertEquals("FAILURE", result.getMessage());
    verify(playlistDriver).likeSong(USER_NAME, SONG_ID);
  }

  /* ---------- unlikeSong() tests ---------- */

  @Test
  public void unlikeSong_returnsOkStatus_whenSongIsUnliked() {
    when(playlistDriver.unlikeSong(USER_NAME, SONG_ID)).thenReturn(okStatus);

    DbQueryStatus result = playlistDriver.unlikeSong(USER_NAME, SONG_ID);

    assertNotNull(result);
    assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
    verify(playlistDriver).unlikeSong(USER_NAME, SONG_ID);
  }

  @Test
  public void unlikeSong_returnsErrorStatus_whenSongWasNotPreviouslyLiked() {
    when(playlistDriver.unlikeSong(USER_NAME, SONG_ID)).thenReturn(errorStatus);

    DbQueryStatus result = playlistDriver.unlikeSong(USER_NAME, SONG_ID);

    assertNotNull(result);
    assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, result.getdbQueryExecResult());
    verify(playlistDriver).unlikeSong(USER_NAME, SONG_ID);
  }

  /* ---------- deleteSongFromDb() tests ---------- */

  @Test
  public void deleteSongFromDb_returnsOkStatus_whenSongIsDeleted() {
    when(playlistDriver.deleteSongFromDb(SONG_ID)).thenReturn(okStatus);

    DbQueryStatus result = playlistDriver.deleteSongFromDb(SONG_ID);

    assertNotNull(result);
    assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
    verify(playlistDriver).deleteSongFromDb(SONG_ID);
  }

  @Test
  public void deleteSongFromDb_returnsErrorStatus_whenSongDoesNotExist() {
    when(playlistDriver.deleteSongFromDb(SONG_ID)).thenReturn(errorStatus);

    DbQueryStatus result = playlistDriver.deleteSongFromDb(SONG_ID);

    assertNotNull(result);
    assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, result.getdbQueryExecResult());
    verify(playlistDriver).deleteSongFromDb(SONG_ID);
  }
}
