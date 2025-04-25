package com.csc301.profilemicroservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ProfileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfileDriverImpl profileDriver;

    @Mock
    private PlaylistDriverImpl playlistDriver;

    @InjectMocks
    private ProfileController profileController;

    @Before
    public void setUp() {
        // Stand up ProfileController in isolation
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    public void testAddProfile() throws Exception {
        // Arrange
        when(profileDriver.createUserProfile("testUser", "Test User", "password123"))
            .thenReturn(new DbQueryStatus("Profile created successfully", DbQueryExecResult.QUERY_OK));

        // Act & Assert
        mockMvc.perform(post("/profile")
                .param(ProfileController.KEY_USER_NAME, "testUser")
                .param(ProfileController.KEY_USER_FULLNAME, "Test User")
                .param(ProfileController.KEY_USER_PASSWORD, "password123")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Profile created successfully"));
    }

    @Test
    public void testFollowFriend() throws Exception {
        // Arrange
        when(profileDriver.followFriend("testUser", "friendUser"))
            .thenReturn(new DbQueryStatus("Friend followed successfully", DbQueryExecResult.QUERY_OK));

        // Act & Assert
        mockMvc.perform(put("/followFriend/{userName}/{friendUserName}", "testUser", "friendUser"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Friend followed successfully"));
    }

    @Test
    public void testGetAllFriendFavouriteSongTitles() throws Exception {
        // Arrange
        when(profileDriver.getAllSongFriendsLike("testUser"))
            .thenReturn(new DbQueryStatus("Fetched friend favorite songs", DbQueryExecResult.QUERY_OK));

        // Act & Assert
        mockMvc.perform(get("/getAllFriendFavouriteSongTitles/{userName}", "testUser"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Fetched friend favorite songs"));
    }

    @Test
    public void testLikeSong() throws Exception {
        // Arrange
        when(playlistDriver.likeSong("testUser", "12345"))
            .thenReturn(new DbQueryStatus("Song liked successfully", DbQueryExecResult.QUERY_OK));

        // Act & Assert
        mockMvc.perform(put("/likeSong/{userName}/{songId}", "testUser", "12345"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Song liked successfully"));
    }

    // ... you can add similar tests for unlikeSong, deleteAllSongsFromDb, addSong, etc.
}
