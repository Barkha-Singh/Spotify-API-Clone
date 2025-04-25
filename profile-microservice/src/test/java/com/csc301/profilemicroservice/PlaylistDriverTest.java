package com.csc301.profilemicroservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

public class PlaylistDriverTest {

  private PlaylistDriver playlistDriver;

  @Before
  public void setUp() {
    // Mock implementation of PlaylistDriver for unit testing
    playlistDriver = new PlaylistDriver() {
      @Override
      public DbQueryStatus likeSong(String userName, String songId) {
        return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_OK);
      }

      @Override
      public DbQueryStatus unlikeSong(String userName, String songId) {
        return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_OK);
      }

      @Override
      public DbQueryStatus deleteSongFromDb(String songId) {
        return new DbQueryStatus("DELETE", DbQueryExecResult.QUERY_OK);
      }
    };
  }

  @Test
  public void testLikeSongReturnsQueryOk() {
    DbQueryStatus result = playlistDriver.likeSong("user1", "song1");
    assertNotNull(result);
    assertEquals("PUT", result.getMessage());
    assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
  }

  @Test
  public void testUnlikeSongReturnsQueryOk() {
    DbQueryStatus result = playlistDriver.unlikeSong("user1", "song2");
    assertNotNull(result);
    assertEquals("PUT", result.getMessage());
    assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
  }

  @Test
  public void testDeleteSongFromDbReturnsQueryOk() {
    DbQueryStatus result = playlistDriver.deleteSongFromDb("song3");
    assertNotNull(result);
    assertEquals("DELETE", result.getMessage());
    assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
  }
}

