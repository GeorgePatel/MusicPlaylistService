package com.amazon.ata.music.playlist.service.dynamodb.models;

import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazon.ata.music.playlist.service.helpers.PlaylistTestHelper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaylistDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @InjectMocks
    private PlaylistDao playlistDao;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        playlistDao = new PlaylistDao(dynamoDBMapper);
    }

    @Test
    void getPlaylist_validId_returnPlaylistFromDynamoDB() {
        // GIVEN - playlist corresponding to the id
        Playlist playlist = PlaylistTestHelper.generatePlaylist();
        String id = playlist.getId();

        // WHEN - call getPlaylist()
        when(dynamoDBMapper.load(Playlist.class, id)).thenReturn(playlist);
        Playlist result = playlistDao.getPlaylist(id);

        // THEN
        assertEquals("id", result.getId(), "The playlist id should be 'id'");
        assertEquals("a playlist", result.getName(), "The playlist name should be 'a playlist'.");
        assertEquals("CustomerABC", result.getCustomerId(), "The playlist customerId should be 'CustomerABC'.");
        assertEquals(1, result.getSongCount(), "The playlist should contain one AlbumTrack.");
        assertNotNull(result.getTags(), "The playlist tags should contain one tag" + result.getTags());
        assertEquals(1, result.getSongList().size(), "The songlist should contain one AlbumTrack" + result.getSongList());
    }

    @Test
    void getPlaylist_nonExistingPlaylistId_throwsPlaylistNotFoundException() {
        // GIVEN - non-existing playlist id in DynamoDB table
        String id = "'/hdksg'";

        // WHEN + THEN - getPlaylist() should throw PlaylistNotFoundException
        assertThrows(PlaylistNotFoundException.class, () -> playlistDao.getPlaylist(id));
    }

    @Test
    void getPlaylist_nullId_throwsPlaylistNotFoundException() {
        // GIVEN - null playlist id
        String id = null;

        // WHEN + THEN - getPlaylist() should throw PlaylistNotFoundException
        assertThrows(PlaylistNotFoundException.class, () -> playlistDao.getPlaylist(id));
    }

    @Test
    void savePlaylist_providePlaylist_savesPlaylistToDynamoDB() {
        // GIVEN - a playlist
        Playlist playlist = PlaylistTestHelper.generatePlaylist();

        // WHEN - call savePlaylist()
        playlistDao.savePlaylist(playlist);

        // THEN - verify playlist was saved to DynamoDB
        verify(dynamoDBMapper).save(playlist);
    }
}
