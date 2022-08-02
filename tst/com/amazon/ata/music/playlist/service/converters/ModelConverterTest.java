package com.amazon.ata.music.playlist.service.converters;

import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;

public class ModelConverterTest {

//    @Mock
//    private Playlist playlist;
//    private AlbumTrack albumTrack;
//    private List<AlbumTrack> albumTracks;
//
//    @InjectMocks
//    private ModelConverter modelConverter;
//
//    @BeforeEach
//    public void setUp() {
//        initMocks(this);
//    }

    @Test
    void toPlaylistModel_validPlaylist_returnsConvertedPlaylistModel() {
        // Given - a playlist containing id, name, customerId, songcount, tags, and songlist

        // When - call toPlaylistModel

        // Then - the attributes of converted PlaylistModel equals the Playlist provided

    }

    @Test
    void toPlaylistModel_playlistWithNoTags_returnsConvertedPlaylistModelWithNoTags() {
        // Given - a playlist containing id, name, customerId, songcount, and songlist but no tags

        // When - call toPlaylistModel

        // Then - the attributes of converted PlaylistModel equals the Playlist provided

    }

    @Test
    void toSongModel_validAlbumTrack_returnsConvertedSongModel() {
        // Given - an AlbumTrack containing asin, tracknumber, album, and title

        // When - call toSongModel

        // Then - the attributes of converted SongModel equals the AlbumTrack provided

    }

    @Test
    void toSongModel_albumTrackWithNoAlbum_returnsConvertedSongModelWithNoAlbum() {
        // Given - an AlbumTrack containing asin, tracknumber, and title but no album

        // When - call toSongModel

        // Then - the attributes of converted SongModel equals the AlbumTrack provided

    }

    @Test
    void toSongModelList_listOfThreeAlbumTracks_returnsListOfThreeSongModels() {
        // Given - a list of three albumTracks

        // When - call toSongModelList

        // Then - the converted SongModelList contains the three AlbumTracks provided

    }

    @Test
    void toSongModelList_emptyListOfAlbumTracks_returnsEmptyListOfSongModels() {
        // Given - a list of empty albumTracks

        // When - call toSongModelList

        // Then - the converted SongModelList is empty

    }
}
