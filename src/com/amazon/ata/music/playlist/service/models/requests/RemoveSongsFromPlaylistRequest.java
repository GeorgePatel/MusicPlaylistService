//package com.amazon.ata.music.playlist.service.models.requests;
//
//import java.util.List;
//import java.util.Objects;
//
//public class RemoveSongsFromPlaylistRequest {
//    private String id;
//    private List<String> asinList;
//    private List<Integer> trackNumberList;
//
//    public RemoveSongsFromPlaylistRequest() {}
//
//    public RemoveSongsFromPlaylistRequest(String id, String asin, int trackNumber) {
//        this.id = id;
//        this.asin = asin;
//        this.trackNumber = trackNumber;
//    }
//
//    public RemoveSongsFromPlaylistRequest(Builder builder) {
//        this.id = builder.id;
//        this.asin = builder.asin;
//        this.trackNumber = builder.trackNumber;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getAsin() {
//        return asin;
//    }
//
//    public void setAsin(String asin) {
//        this.asin = asin;
//    }
//
//    public int getTrackNumber() {
//        return trackNumber;
//    }
//
//    public void setTrackNumber(int trackNumber) {
//        this.trackNumber = trackNumber;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        RemoveSongsFromPlaylistRequest that = (RemoveSongsFromPlaylistRequest) o;
//        return getTrackNumber() == that.getTrackNumber() && Objects.equals(getId(), that.getId()) && Objects.equals(getAsin(), that.getAsin());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getAsin(), getTrackNumber());
//    }
//
//    @Override
//    public String toString() {
//        return "RemoveSongFromPlaylistRequest{" +
//                "id='" + id + '\'' +
//                ", asin='" + asin + '\'' +
//                ", trackNumber=" + trackNumber +
//                '}';
//    }
//
//    public static Builder builder() {return new Builder();}
//
//    public static final class Builder {
//        private String id;
//        private String asin;
//        private int trackNumber;
//
//        private Builder() {}
//
//        public Builder with(String idToUse) {
//            this.id = idToUse;
//            return this;
//        }
//
//        public Builder withAsin(String asinToUse) {
//            this.asin = asinToUse;
//            return this;
//        }
//
//        public Builder withTrackNumber(int trackNumberToUse) {
//            this.trackNumber = trackNumberToUse;
//            return this;
//        }
//
//        public RemoveSongsFromPlaylistRequest build() {
//            return new RemoveSongsFromPlaylistRequest(this);
//        }
//    }
//}
