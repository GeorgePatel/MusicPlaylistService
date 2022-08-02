package com.amazon.ata.music.playlist.service.dynamodb.models;

import com.amazon.ata.music.playlist.service.helpers.PlaylistTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistTest {

    private Playlist playlist;

    @BeforeEach
    public void setUp() {
        playlist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(2);
    }

    @Test
    void getId_returnsId() {
        // WHEN - call getId()
        String result = playlist.getId();

        // THEN - returns the Id
        assertEquals("id", result, "The id for a test playlist should be 'id'.");
    }

    @Test
    void getName_returnsName() {
        // WHEN - call getName()
        String result = playlist.getName();

        // THEN - returns the name
        assertEquals("a playlist", result, "The name for a test playlist should be 'a playlist'.");
    }

    @Test
    void getCustomerId_returnsCustomerId() {
        // WHEN - call getCustomerId()
        String result = playlist.getCustomerId();

        // THEN - returns the customerId
        assertEquals("CustomerABC", result, "The customerId for a test playlist should be 'CustomerABC'.");
    }

    @Test
    void getSongCount_returnsSongCount() {
        // WHEN - call getSongCount()
        Integer result = playlist.getSongCount();

        // THEN - returns the SongCount
        assertEquals(Integer.valueOf(2), result, "The songCount for a test playlist should be 2.");
    }

    /*@Test
    void getTags_returnsTags() {
        // WHEN - call getTags()

        // THEN - returns the Tags
    }

    @Test
    void getSongList_returnsSongList() {
        // WHEN - call getSongList()

        // THEN - returns the SongList
    }*/

    @Test
    void setId_provideId_updatesId() {
        // GIVEN - id
        String id = "pl5446";

        // WHEN - call setId()
        playlist.setId(id);

        // THEN - updates the id
        assertEquals(id, playlist.getId(), "The id for the test playlist should've been changed to " + id);
    }

    @Test
    void setName_provideName_updatesName() {
        // GIVEN - Name
        String name = "funky";

        // WHEN - call setName()
        playlist.setName(name);

        // THEN - updates the Name
        assertEquals(name, playlist.getName(), "The name for the test playlist should've been changed to " + name);
    }

    @Test
    void setCustomerId_provideCustomerId_updatesCustomerId() {
        // GIVEN - CustomerId
        String customerId = "na4585";

        // WHEN - call setCustomerId()
        playlist.setCustomerId(customerId);

        // THEN - updates the CustomerId
        assertEquals(customerId, playlist.getCustomerId(), "The customerId for the test playlist should've been changed to " + customerId);
    }

    @Test
    void setSongCount_provideSongCount_updatesSongCount() {
        // GIVEN - SongCount
        Integer songCount = Integer.valueOf(4);

        // WHEN - call setSongCount()
        playlist.setSongCount(songCount);

        // THEN - updates the SongCount
        assertEquals(songCount, playlist.getSongCount(), "The songCount for the test playlist should've been changed to " + songCount);
    }

    /*@Test
    void setTags_provideTags_updatesTags() {
        // GIVEN - a set of Tags

        // WHEN - call setTags()

        // THEN - updates the Tags
    }

    @Test
    void setSongList_provideAlbumTracks_updatesSongList() {
        // GIVEN - a list of AlbumTracks

        // WHEN - call setSongList()

        // THEN - updates the songList
    }*/
}
