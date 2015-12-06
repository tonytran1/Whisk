package com.example.team3.whisk;

/**
 * Created by LIBEXTMAC on 12/3/15.
 */
import java.util.List;

/**
 * Created by Tony on 12/3/2015.
 */
public class MapsResponse
{

    /**
     * results : [{"address_components":[{"long_name":"1600","short_name":"1600","types":["street_number"]},{"long_name":"Amphitheatre Parkway","short_name":"Amphitheatre Pkwy","types":["route"]},{"long_name":"Mountain View","short_name":"Mountain View","types":["locality","political"]},{"long_name":"Santa Clara County","short_name":"Santa Clara County","types":["administrative_area_level_2","political"]},{"long_name":"California","short_name":"CA","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]},{"long_name":"94043","short_name":"94043","types":["postal_code"]}],"formatted_address":"1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA","geometry":{"location":{"lat":37.4220355,"lng":-122.0841244},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":37.4233844802915,"lng":-122.0827754197085},"southwest":{"lat":37.4206865197085,"lng":-122.0854733802915}}},"place_id":"ChIJ2eUgeAK6j4ARbn5u_wAGqWA","types":["street_address"]}]
     * status : OK
     */

    private String status;
    /**
     * address_components : [{"long_name":"1600","short_name":"1600","types":["street_number"]},{"long_name":"Amphitheatre Parkway","short_name":"Amphitheatre Pkwy","types":["route"]},{"long_name":"Mountain View","short_name":"Mountain View","types":["locality","political"]},{"long_name":"Santa Clara County","short_name":"Santa Clara County","types":["administrative_area_level_2","political"]},{"long_name":"California","short_name":"CA","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]},{"long_name":"94043","short_name":"94043","types":["postal_code"]}]
     * formatted_address : 1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA
     * geometry : {"location":{"lat":37.4220355,"lng":-122.0841244},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":37.4233844802915,"lng":-122.0827754197085},"southwest":{"lat":37.4206865197085,"lng":-122.0854733802915}}}
     * place_id : ChIJ2eUgeAK6j4ARbn5u_wAGqWA
     * types : ["street_address"]
     */

    private List<ResultsEntity> results;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        private String formatted_address;
        /**
         * location : {"lat":37.4220355,"lng":-122.0841244}
         * location_type : ROOFTOP
         * viewport : {"northeast":{"lat":37.4233844802915,"lng":-122.0827754197085},"southwest":{"lat":37.4206865197085,"lng":-122.0854733802915}}
         */

        private GeometryEntity geometry;
        private String place_id;
        /**
         * long_name : 1600
         * short_name : 1600
         * types : ["street_number"]
         */

        private List<AddressComponentsEntity> address_components;
        private List<String> types;

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public void setGeometry(GeometryEntity geometry) {
            this.geometry = geometry;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public void setAddress_components(List<AddressComponentsEntity> address_components) {
            this.address_components = address_components;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public GeometryEntity getGeometry() {
            return geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public List<AddressComponentsEntity> getAddress_components() {
            return address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public static class GeometryEntity {
            /**
             * lat : 37.4220355
             * lng : -122.0841244
             */

            private LocationEntity location;
            private String location_type;
            /**
             * northeast : {"lat":37.4233844802915,"lng":-122.0827754197085}
             * southwest : {"lat":37.4206865197085,"lng":-122.0854733802915}
             */

            private ViewportEntity viewport;

            public void setLocation(LocationEntity location) {
                this.location = location;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public void setViewport(ViewportEntity viewport) {
                this.viewport = viewport;
            }

            public LocationEntity getLocation() {
                return location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public ViewportEntity getViewport() {
                return viewport;
            }

            public static class LocationEntity {
                private double lat;
                private double lng;

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }

                public double getLat() {
                    return lat;
                }

                public double getLng() {
                    return lng;
                }
            }

            public static class ViewportEntity {
                /**
                 * lat : 37.4233844802915
                 * lng : -122.0827754197085
                 */

                private NortheastEntity northeast;
                /**
                 * lat : 37.4206865197085
                 * lng : -122.0854733802915
                 */

                private SouthwestEntity southwest;

                public void setNortheast(NortheastEntity northeast) {
                    this.northeast = northeast;
                }

                public void setSouthwest(SouthwestEntity southwest) {
                    this.southwest = southwest;
                }

                public NortheastEntity getNortheast() {
                    return northeast;
                }

                public SouthwestEntity getSouthwest() {
                    return southwest;
                }

                public static class NortheastEntity {
                    private double lat;
                    private double lng;

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }

                    public double getLat() {
                        return lat;
                    }

                    public double getLng() {
                        return lng;
                    }
                }

                public static class SouthwestEntity {
                    private double lat;
                    private double lng;

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }

                    public double getLat() {
                        return lat;
                    }

                    public double getLng() {
                        return lng;
                    }
                }
            }
        }

        public static class AddressComponentsEntity {
            private String long_name;
            private String short_name;
            private List<String> types;

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }

            public String getLong_name() {
                return long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public List<String> getTypes() {
                return types;
            }
        }
    }
}
