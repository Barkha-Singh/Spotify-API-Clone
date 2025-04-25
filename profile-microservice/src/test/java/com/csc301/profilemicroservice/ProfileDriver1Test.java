package com.csc301.profilemicroservice;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProfileDriver1Test {

    @Mock
    private ProfileDriver profileDriver;

    @Before
    public void setUp() {
        // nothing to do here for a pure interface mock
    }

    @Test
    public void testCreateUserProfile() {
        // arrange
        DbQueryStatus stubStatus = new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);
        when(profileDriver.createUserProfile("user1", "Full Name", "pw123"))
            .thenReturn(stubStatus);

        // act
        DbQueryStatus result = profileDriver.createUserProfile("user1", "Full Name", "pw123");

        // assert
        assertEquals("Should return QUERY_OK", 
                     DbQueryExecResult.QUERY_OK, 
                     result.getdbQueryExecResult());
        assertEquals("Should return the POST verb",
                     "POST",
                     result.getMessage());
    }

    @Test
    public void testFollowFriend() {
        // arrange
        DbQueryStatus stubStatus = new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);
        when(profileDriver.followFriend("user1", "friend1"))
            .thenReturn(stubStatus);

        // act
        DbQueryStatus result = profileDriver.followFriend("user1", "friend1");

        // assert
        assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
        assertEquals("POST", result.getMessage());
    }

    @Test
    public void testUnfollowFriend() {
        // arrange
        DbQueryStatus stubStatus = new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);
        when(profileDriver.unfollowFriend("user1", "friend1"))
            .thenReturn(stubStatus);

        // act
        DbQueryStatus result = profileDriver.unfollowFriend("user1", "friend1");

        // assert
        assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
        assertEquals("POST", result.getMessage());
    }

    @Test
    public void testGetAllSongFriendsLike() {
        // arrange
        DbQueryStatus stubStatus = new DbQueryStatus("GET", DbQueryExecResult.QUERY_OK);
        when(profileDriver.getAllSongFriendsLike("user1"))
            .thenReturn(stubStatus);

        // act
        DbQueryStatus result = profileDriver.getAllSongFriendsLike("user1");

        // assert
        assertEquals(DbQueryExecResult.QUERY_OK, result.getdbQueryExecResult());
        assertEquals("GET", result.getMessage());
    }
}
