package com.amazon.ata.music.playlist.service.lambda;

import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.dependency.ServiceComponent;
import com.amazon.ata.music.playlist.service.models.requests.RemovePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.RemovePlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class RemovePlaylistActivityProvider implements RequestHandler<RemovePlaylistRequest, RemovePlaylistResult> {
    private static ServiceComponent serviceComponent;

    public RemovePlaylistActivityProvider() {

    }

    @Override
    public RemovePlaylistResult handleRequest(final RemovePlaylistRequest removePlaylistRequest, Context context) {
        return getServiceComponent().provideRemovePlaylistActivity().handleRequest(removePlaylistRequest, context);
    }

    private ServiceComponent getServiceComponent() {
        if (serviceComponent == null) {
            serviceComponent = DaggerServiceComponent.create();
        }
        return serviceComponent;
    }
}
