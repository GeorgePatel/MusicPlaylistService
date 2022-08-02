package com.amazon.ata.music.playlist.service.models;

import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.amazon.ata.music.playlist.service.helpers.AlbumTrackTestHelper.generateAlbumTrack;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistModelTest {

    @Test
    void withId_provideId_returnsId() {
        // GIVEN - id
        String id = "testId";

        // WHEN - call withId() with builder
        PlaylistModel result = PlaylistModel.builder().withId(id).build();

        // THEN - returns the Id
        assertEquals(id , result.getId() , "The id for the playlist model should be 'testId'.");
    }

    @Test
    void withName_provideName_returnsName() {
        // GIVEN - name
        String name = "testName";

        // WHEN - call withName() with builder
        PlaylistModel result = PlaylistModel.builder().withName(name).build();

        // THEN - returns the name
        assertEquals(name, result.getName(), "The name for the playlist model should be 'testName'.");
    }

    @Test
    void withCustomerId_provideCustomerId_returnsCustomerId() {
        // GIVEN - customerId
        String customerId = "testCustomerId";

        // WHEN - call withCustomerId() with builder
        PlaylistModel result = PlaylistModel.builder().withCustomerId(customerId).build();

        // THEN - returns the customerId
        assertEquals(customerId, result.getCustomerId(), "The customerId for the playlist model should be 'testCustomerId'.");
    }

    @Test
    void withSongCount_provideSongCount_returnsSongCount() {
        // GIVEN - song count
        Integer songCount = 2;

        // WHEN - call withSongCount() with builder
        PlaylistModel result = PlaylistModel.builder().withSongCount(songCount).build();

        // THEN - returns the SongCount
        assertEquals(songCount, result.getSongCount(), "The songCount for the playlist model should be 2.");
    }

    @Test
    void withTags_provideTags_updatesTags() {
        // GIVEN - tags
        List<String> tags = List.of("tag1", "tag2");
        // WHEN - call withTags() with builder
       PlaylistModel result = PlaylistModel.builder().withTags(tags).build();

        // THEN - updates the tags
        assertEquals(tags, result.getTags(), "The tags for the test playlist should be " + tags);
    }

    @Test
    void withSongList_provideAlbumTracks_updatesSongList() {
        // GIVEN - album tracks
        List<AlbumTrack> songList = new ArrayList<>();
        AlbumTrack albumTrack1 = generateAlbumTrack(1);
        AlbumTrack albumTrack2 = generateAlbumTrack(2);
        songList.add(albumTrack1);
        songList.add(albumTrack2);

        // WHEN - call setName()
        PlaylistModel result = PlaylistModel.builder().withSongList(songList).build();

        // THEN - updates the Name
        assertEquals(songList, result.getSongList(), "The playlist should contain 2 album tracks.");
    }
}
