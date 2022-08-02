package com.amazon.ata.music.playlist.service.models.requests;

import java.util.Objects;

public class RemovePlaylistRequest {
    private String id;

    public RemovePlaylistRequest () {
    }

    public RemovePlaylistRequest(Builder builder) {this.id = builder.id;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemovePlaylistRequest that = (RemovePlaylistRequest) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "RemovePlaylistRequest{" +
                "id='" + id + '\'' +
                '}';
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String id;

        private Builder() {

        }

        public Builder withId(String idToUse) {
            this.id = idToUse;
            return this;
        }

        public RemovePlaylistRequest build() { return new RemovePlaylistRequest(this); }
    }
}
