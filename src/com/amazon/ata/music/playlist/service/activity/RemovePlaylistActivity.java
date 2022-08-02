package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.converters.ModelConverter;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeValueException;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import com.amazon.ata.music.playlist.service.models.requests.RemovePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.RemovePlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

import static com.amazon.ata.music.playlist.service.util.MusicPlaylistServiceUtils.isValidString;

public class RemovePlaylistActivity implements RequestHandler<RemovePlaylistRequest, RemovePlaylistResult> {
    private final Logger log = LogManager.getLogger();
    private final PlaylistDao playlistDao;

    /**
     * Instantiates a new RemovePlaylistActivity object.
     *
     * @param playlistDao PlaylistDao to access the playlists table.
     */
    @Inject
    public RemovePlaylistActivity(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    /**
     * This method handles the incoming request by removing the playlist from the database
     * with the provided playlist ID from the request.
     * <p>
     * It then returns the removed playlist.
     * <p>
     * If the provided playlist ID does not exist, throws a PlaylistNotFoundException
     *
     * If the provided playlist ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param removePlaylistRequest request object containing the playlist ID associated with it
     * @return removePlaylistResult result object containing the API defined {@link PlaylistModel}
     */
    @Override
    public RemovePlaylistResult handleRequest(final RemovePlaylistRequest removePlaylistRequest, Context context) {
        log.info("Received RemovePlaylistRequest {}", removePlaylistRequest);

        String playlistId;

        if (!isValidString(removePlaylistRequest.getId())) {
            throw new InvalidAttributeValueException("Contains invalid characters.");
        } else {
            playlistId = removePlaylistRequest.getId();
        }

        Playlist playlistToRemove = playlistDao.getPlaylist(playlistId);

        if (playlistToRemove == null) {
            throw new PlaylistNotFoundException("Given playlist ID is not found in the database.");
        }

        playlistDao.removePlaylist(playlistToRemove);
        PlaylistModel playlistModel = new ModelConverter().toPlaylistModel(playlistToRemove);

        return RemovePlaylistResult.builder()
                .withPlaylist(playlistModel)
                .build();
    }
}
