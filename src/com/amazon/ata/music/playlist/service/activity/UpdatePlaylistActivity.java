package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeChangeException;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeValueException;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import com.amazon.ata.music.playlist.service.models.requests.UpdatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.UpdatePlaylistResult;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.amazon.ata.music.playlist.service.util.MusicPlaylistServiceUtils.isValidString;

/**
 * Implementation of the UpdatePlaylistActivity for the MusicPlaylistService's UpdatePlaylist API.
 *
 * This API allows the customer to update their saved playlist's information.
 */
public class UpdatePlaylistActivity implements RequestHandler<UpdatePlaylistRequest, UpdatePlaylistResult> {
    private final Logger log = LogManager.getLogger();
    private final PlaylistDao playlistDao;

    /**
     * Instantiates a new UpdatePlaylistActivity object.
     *
     * @param playlistDao PlaylistDao to access the playlist table.
     */
    @Inject
    public UpdatePlaylistActivity(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    /**
     * This method handles the incoming request by retrieving the playlist, updating it,
     * and persisting the playlist.
     * <p>
     * It then returns the updated playlist.
     * <p>
     * If the playlist does not exist, this should throw a PlaylistNotFoundException.
     * <p>
     * If the provided playlist name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     * <p>
     * If the request tries to update the customer ID,
     * this should throw an InvalidAttributeChangeException
     *
     * @param updatePlaylistRequest request object containing the playlist ID, playlist name, and customer ID
     *                              associated with it
     * @return updatePlaylistResult result object containing the API defined {@link PlaylistModel}
     */
    @Override
    public UpdatePlaylistResult handleRequest(final UpdatePlaylistRequest updatePlaylistRequest, Context context) {
        log.info("Received UpdatePlaylistRequest {}", updatePlaylistRequest);
        if (!isValidString(updatePlaylistRequest.getName())) {
            throw new InvalidAttributeValueException("Provided name has invalid character values.");
        }

        if (!isValidString(updatePlaylistRequest.getCustomerId())) {
            throw new InvalidAttributeValueException("Provided customerID has invalid character values.");
        }

        String playlistId = updatePlaylistRequest.getId();
        Playlist playlist = playlistDao.getPlaylist(playlistId);

        if (playlist == null) {
            throw new PlaylistNotFoundException("Given playlist ID is not found in the database.");
        }

        if (!Objects.equals(updatePlaylistRequest.getCustomerId(), playlist.getCustomerId())) {
            throw new InvalidAttributeChangeException("Customer ID cannot be updated");
        }

        playlist.setName(updatePlaylistRequest.getName());
        playlistDao.savePlaylist(playlist);

        Integer songCount = playlist.getSongCount();
        if (songCount == null) {
            songCount = 0;
        }

        List<String> tagsList = new ArrayList<>(playlist.getTags());
        PlaylistModel playlistModel = PlaylistModel.builder()
                                        .withId(playlist.getId())
                                        .withName(playlist.getName())
                                        .withCustomerId(playlist.getCustomerId())
                                        .withSongCount(songCount)
                                        .withSongList(playlist.getSongList())
                                        .withTags(tagsList)
                                        .build();

        return UpdatePlaylistResult.builder()
                .withPlaylist(playlistModel)
                .build();
    }
}
