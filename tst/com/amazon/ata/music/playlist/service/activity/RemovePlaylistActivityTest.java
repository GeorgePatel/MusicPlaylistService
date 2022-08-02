package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeValueException;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazon.ata.music.playlist.service.models.requests.RemovePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.RemovePlaylistResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RemovePlaylistActivityTest {
    @Mock
    private PlaylistDao playlistDao;

    @InjectMocks
    private RemovePlaylistActivity removePlaylistActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        removePlaylistActivity = new RemovePlaylistActivity(playlistDao);
    }

    @Test
    public void handleRequest_savedPlaylistFound_returnsRemovedPlaylistModelInResult() {
        // GIVEN
        String expectedId = "expectedId";
        String expectedName = "expectedName";
        String expectedCustomerId = "expectedCustomerId";
        int expectedSongCount = 0;
        List<String> expectedTags = Lists.newArrayList("tag");

        Playlist playlist = new Playlist();
        playlist.setId(expectedId);
        playlist.setName(expectedName);
        playlist.setCustomerId(expectedCustomerId);
        playlist.setSongCount(expectedSongCount);
        playlist.setTags(Sets.newHashSet(expectedTags));

        RemovePlaylistRequest request = RemovePlaylistRequest.builder()
                .withId(expectedId)
                .build();

        // WHEN
        when(playlistDao.getPlaylist(playlist.getId())).thenReturn(playlist);
        RemovePlaylistResult result = removePlaylistActivity.handleRequest(request, null);

        // THEN
        verify(playlistDao).removePlaylist(playlist);
        assertEquals(expectedId, result.getPlaylist().getId());
        assertEquals(expectedName, result.getPlaylist().getName());
        assertEquals(expectedCustomerId, result.getPlaylist().getCustomerId());
        assertEquals(expectedSongCount, result.getPlaylist().getSongCount());
        assertEquals(expectedTags, result.getPlaylist().getTags());
    }

    @Test
    public void handleRequest_playlistNotFound_ThrowsPlaylistNotFoundException() {
        // GIVEN
        RemovePlaylistRequest request = RemovePlaylistRequest.builder()
                .withId("doesNotExist")
                .build();

        // WHEN + THEN
        assertThrows(PlaylistNotFoundException.class, () -> removePlaylistActivity.handleRequest(request, null));
    }

    @Test
    public void handleRequest_invalidPlaylistId_ThrowsInvalidAttributeValueException() {
        // GIVEN
        RemovePlaylistRequest request = RemovePlaylistRequest.builder()
                .withId("in'va/id")
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> removePlaylistActivity.handleRequest(request, null));
    }
}
