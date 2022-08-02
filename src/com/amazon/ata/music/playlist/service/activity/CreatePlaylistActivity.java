package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.converters.ModelConverter;
import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeValueException;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.CreatePlaylistResult;
import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;

import com.amazon.ata.music.playlist.service.util.MusicPlaylistServiceUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.amazon.ata.music.playlist.service.util.MusicPlaylistServiceUtils.isValidString;

/**
 * Implementation of the CreatePlaylistActivity for the MusicPlaylistService's CreatePlaylist API.
 *
 * This API allows the customer to create a new playlist with no songs.
 */
public class CreatePlaylistActivity implements RequestHandler<CreatePlaylistRequest, CreatePlaylistResult> {
    private final Logger log = LogManager.getLogger();
    private final PlaylistDao playlistDao;

//    /*
//    No-arg default constructor for AWS Lambda
//     */
//    public CreatePlaylistActivity() {
//        this.playlistDao = getPlaylistDao();
//    }
//
//    /*
//    Getter for the playlistDao
//     */
//    public PlaylistDao getPlaylistDao() {
//        return playlistDao;
//    }

    /**
     * Instantiates a new CreatePlaylistActivity object.
     *
     * @param playlistDao PlaylistDao to access the playlists table.
     */
    @Inject
    public CreatePlaylistActivity(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    /**
     * This method handles the incoming request by persisting a new playlist
     * with the provided playlist name and customer ID from the request.
     * <p>
     * It then returns the newly created playlist.
     * <p>
     * If the provided playlist name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createPlaylistRequest request object containing the playlist name and customer ID
     *                              associated with it
     * @return createPlaylistResult result object containing the API defined {@link PlaylistModel}
     */
    @Override
    public CreatePlaylistResult handleRequest(final CreatePlaylistRequest createPlaylistRequest, Context context) {
        log.info("Received CreatePlaylistRequest {}", createPlaylistRequest);

        String customerId;

        if (!isValidString(createPlaylistRequest.getCustomerId())) {
            throw new InvalidAttributeValueException("Contains invalid characters.");
        } else {
            customerId = createPlaylistRequest.getCustomerId();
        }

        String name;

        if (!isValidString(createPlaylistRequest.getName())) {
            throw new InvalidAttributeValueException("Contains invalid characters.");
        } else {
            name = createPlaylistRequest.getName();
        }

        String playlistId = MusicPlaylistServiceUtils.generatePlaylistId();

        List<AlbumTrack> songList = new ArrayList<>();

        Set<String> tags = null;
        if (createPlaylistRequest.getTags() != null) {
            tags = new HashSet<>(createPlaylistRequest.getTags());
        }

        Playlist newPlaylist = new Playlist();
        newPlaylist.setId(playlistId);
        newPlaylist.setName(name);
        newPlaylist.setCustomerId(customerId);
        newPlaylist.setTags(tags);
        newPlaylist.setSongList(songList);
        newPlaylist.setSongCount(0);
        playlistDao.savePlaylist(newPlaylist);

        PlaylistModel playlistModel = new ModelConverter().toPlaylistModel(newPlaylist);

        return CreatePlaylistResult.builder()
                .withPlaylist(playlistModel)
                .build();
    }
}
