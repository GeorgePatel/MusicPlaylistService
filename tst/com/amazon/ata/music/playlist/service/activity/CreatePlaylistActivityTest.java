package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeException;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.CreatePlaylistResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreatePlaylistActivityTest {
    @Mock
    private PlaylistDao playlistDao;

    @InjectMocks
    private CreatePlaylistActivity createPlaylistActivity;

    @BeforeEach
    private void setUp() {
        initMocks(this);
        createPlaylistActivity = new CreatePlaylistActivity(playlistDao);
    }

    @Test
    void handleRequest_validRequest_createsNewPlaylistWithEmptySongList() {
        // GIVEN - CreatePlaylistRequest with valid name, customerId and a list of tags
        String name = "funk";
        String customerId = "4756";
        List<String> tags = List.of("tag1", "tag2");
        CreatePlaylistRequest request = new CreatePlaylistRequest(name, customerId, tags);

        // WHEN - call handleRequest()
        CreatePlaylistResult result = createPlaylistActivity.handleRequest(request, null);

        // THEN - returns PlaylistModel with randomized id, name, customerId, zero songCount, provided tags and
        // an empty songlist
        verify(playlistDao).savePlaylist(any());
        assertNotNull(result.getPlaylist().getId(), "A random playlist id should be generated");
        assertEquals(name, result.getPlaylist().getName(), "The playlist name should be " + name);
        assertEquals(customerId, result.getPlaylist().getCustomerId(), "The playlist customerId should be " + customerId);
        assertEquals(0, result.getPlaylist().getSongCount(), "The new playlist should contain zero songs.");
        assertEquals(tags, result.getPlaylist().getTags(), "The playlist tags should be " + tags);
        assertEquals(0, result.getPlaylist().getSongList().size(), "The songlist should be empty.");
    }

    @Test
    void handleRequest_invalidPlaylistName_throwsInvalidAttributeValueException() {
        // GIVEN - CreatePlaylistRequest with INVALID name, customerId and a list of tags
        String name = "fun'k";
        String customerId = "4756";
        List<String> tags = List.of("tag1", "tag2");
        CreatePlaylistRequest request = new CreatePlaylistRequest(name, customerId, tags);

        // WHEN + THEN - handleRequest() should throw an InvalidAttributeValueException
        assertThrows(InvalidAttributeException.class, () -> createPlaylistActivity.handleRequest(request, null));
    }

    @Test
    void handleRequest_invalidPlaylistCustomerId_throwsInvalidAttributeValueException() {
        // GIVEN - CreatePlaylistRequest with name, INVALID customerId and a list of tags
        String name = "funk";
        String customerId = "4'756";
        List<String> tags = List.of("tag1", "tag2");
        CreatePlaylistRequest request = new CreatePlaylistRequest(name, customerId, tags);

        // WHEN + THEN - handleRequest() should throw an InvalidAttributeValueException
        assertThrows(InvalidAttributeException.class, () -> createPlaylistActivity.handleRequest(request, null));
    }

    @Test
    void handleRequest_withNoTags_createsNewPlaylistWithNullTags() {
        // GIVEN - CreatePlaylistRequest with valid name, customerId and NO tags
        String name = "funk";
        String customerId = "4756";
        List<String> tags = null;
        CreatePlaylistRequest request = new CreatePlaylistRequest(name, customerId, tags);

        // WHEN - call handleRequest()
        CreatePlaylistResult result = createPlaylistActivity.handleRequest(request, null);

        // THEN - returns PlaylistModel with randomized id, name, customerId, zero songCount, NULL list of tags and
        // an empty songlist
        verify(playlistDao).savePlaylist(any());
        assertNotNull(result.getPlaylist().getId(), "A random playlist id should be generated");
        assertEquals(name, result.getPlaylist().getName(), "The playlist name should be " + name);
        assertEquals(customerId, result.getPlaylist().getCustomerId(), "The playlist customerId should be " + customerId);
        assertEquals(0, result.getPlaylist().getSongCount(), "The new playlist should contain zero songs.");
        assertNull(result.getPlaylist().getTags(), "The playlist tags should be null.");
        assertEquals(0, result.getPlaylist().getSongList().size(), "The songlist should be empty.");
    }
}
