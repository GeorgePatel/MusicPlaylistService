package com.amazon.ata.music.playlist.service.converters;

import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.models.SongModel;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {
    /**
     * Converts a provided {@link Playlist} into a {@link PlaylistModel} representation.
     * @param playlist the playlist to convert
     * @return the converted playlist
     */
    public PlaylistModel toPlaylistModel(Playlist playlist) {
        List<String> tags = null;
        if (playlist.getTags() != null) {
            tags = new ArrayList<>(playlist.getTags());
        }

//        Integer songCount = playlist.getSongCount();
//        if (songCount == null) {
//            songCount = 0;
//        }

        return PlaylistModel.builder()
            .withId(playlist.getId())
            .withName(playlist.getName())
            .withCustomerId(playlist.getCustomerId())
            .withSongCount(playlist.getSongCount())
            .withTags(tags)
            .withSongList(playlist.getSongList())
            .build();
    }

    /**
     * Converts a provided {@link AlbumTrack} into a {@link SongModel} representation.
     * @param albumTrack the album track to convert
     * @return the converted SongModel from an AlbumTrack
     */
    public SongModel toSongModel(AlbumTrack albumTrack) {

        return SongModel.builder()
                .withAsin(albumTrack.getAsin())
                .withTrackNumber(albumTrack.getTrackNumber())
                .withAlbum(albumTrack.getAlbumName())
                .withTitle(albumTrack.getSongTitle())
                .build();
    }

    /**
     * Converts a list of provided {@link AlbumTrack} into a list of {@link SongModel} representation.
     * @param albumTracks the list of album tracks to convert
     * @return the converted list of SongModels from the list of AlbumTracks
     */
    public List<SongModel> toSongModelList(List<AlbumTrack> albumTracks) {

        List<SongModel> songModelList = new ArrayList<>();

        for (AlbumTrack song:
             albumTracks) {
            SongModel songModel = SongModel.builder()
                    .withAsin(song.getAsin())
                    .withTrackNumber(song.getTrackNumber())
                    .withAlbum(song.getAlbumName())
                    .withTitle(song.getSongTitle())
                    .build();
            songModelList.add(songModel);
        }

        return songModelList;
    }
}
