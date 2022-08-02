package com.amazon.ata.music.playlist.service.dynamodb;

import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;

/**
 * Accesses data for a playlist using {@link Playlist} to represent the model in DynamoDB.
 */
public class PlaylistDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a PlaylistDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the playlists table
     */
    @Inject
    public PlaylistDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the {@link Playlist} corresponding to the specified id.
     *
     * @param id the Playlist ID
     * @return the stored Playlist, or null if none was found.
     */
    public Playlist getPlaylist(String id) {
        Playlist playlist = this.dynamoDbMapper.load(Playlist.class, id);

        if (playlist == null) {
            throw new PlaylistNotFoundException("Could not find playlist with id: " + id);
        }

        return playlist;
    }

//    /**
//     * Save a Playlist corresponding with this PlaylistId in our DynamoDB table
//     *
//     * @param id - a unique playlist id assigned to our Playlist in our Database
//     */
//    public void savePlayList(String id) {
//        Playlist playlist = dynamoDbMapper.load(Playlist.class, id);
//        dynamoDbMapper.save(playlist);
//    }

    /**
     * Save the provided Playlist in our DynamoDB table
     *
     * @param playlist - a playlist that been created or updated
     * @return the playlist that been saved in our Database
     */
    public Playlist savePlaylist(Playlist playlist) {
        dynamoDbMapper.save(playlist);
        return playlist;
    }

    /**
     * Remove the provided Playlist in our DynamoDB table
     *
     * @param playlist - a playlist that needs to be removed from our database
     */
    public void removePlaylist(Playlist playlist) {
        dynamoDbMapper.delete(playlist);
    }
}
