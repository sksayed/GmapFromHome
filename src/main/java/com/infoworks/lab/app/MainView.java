package com.infoworks.lab.app;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.PlacesApi;
import com.google.maps.TextSearchRequest;
import com.google.maps.model.PlacesSearchResponse;
import com.infoworks.lab.domain.Models.AuthResponse;
import com.infoworks.lab.domain.datasource.LoginDataSource;
import com.infoworks.lab.gmap.GoogleMapComponent;
import com.infoworks.lab.gmap.JsonConverter;
import com.infoworks.lab.gmap.LatLon;
import com.infoworks.lab.gmap.MapBounds;
import com.itsoul.lab.client.APIContext;
import com.itsoul.lab.domain.base.SortOrder;
import com.itsoul.lab.domain.models.messaging.Message;
import com.itsoul.lab.domain.models.messaging.events.EventType;
import com.itsoul.lab.domain.models.pipeline.GeoEventFilter;
import com.itsoul.lab.domain.models.pipeline.GeoLocation;
import com.itsoul.lab.domain.models.pipeline.GeoTrackerInfo;
import com.itsoul.lab.domain.models.pipeline.GeoTrackingEvent;
import com.itsoul.lab.domain.models.utility.SearchQuery;
import com.itsoul.lab.interactor.exceptions.UnauthorizedAccess;
import com.itsoul.lab.interactor.factory.Interactors;
import com.itsoul.lab.interactor.implementations.MessagingTemplate;
import com.itsoul.lab.interactor.implementations.SocketType;
import com.itsoul.lab.interactor.interfaces.Interactor;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Route("dashboard")

public class MainView extends VerticalLayout {
    public static String API_KEY = "AIzaSyA-ILRTlV8Gd-GvTXEP1oUQej4Q9ZDhe1k";
    private HorizontalLayout searchLayout;
    Button searchButton;
    TextField searchField;
    private ExecutorService serviceExecutor = Executors.newSingleThreadExecutor();
    private Future motion;
    private int i = 0;
    //for getting this radius inside the lamda functions
    String radiusStr = "1000";
    Double radius ;
    UI ui = UI.getCurrent();

    private Button showMotion = new Button("Motion");
    private static final long serialVersionUID = 1L;

    private GoogleMapComponent gm;

    public MainView() {
        setSizeFull();
        gm = new GoogleMapComponent();

        //this one from android apps
        //gm.setApiKey("AIzaSyA-ILRTlV8Gd-GvTXEP1oUQej4Q9ZDhe1k");

        //this one from TenantHome
        gm.setApiKey("AIzaSyAhka1d1MU3fAYcajhyAdGhSdRWqXxhuFY");
        // or:
        // gm.setClientId("CLIENTID");
        initContents();
        initListeners();
    }

    private void initContents() {
        this.searchLayout = new HorizontalLayout();
        this.searchButton = buildSearchButton();
        this.searchField = new TextField();
        searchLayout.add(searchField, searchButton);
        add(searchLayout, gm, showMotion);
        //addMarker();
    }

    private Button buildSearchButton() {
        //TODO: do modifications of the button here
        Button searchButton = new Button("Search");
        return searchButton;
//        searchButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
//        searchButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
    }

    private void initListeners() {


        this.showMotion.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                startMotion(UI.getCurrent());
            }
        });

        this.searchButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            //parse search string for radius
            String searchStr = searchField.getValue().trim();
            String[] subs = searchStr.split("#");

                    radius = Double.valueOf(radiusStr);
            if (subs.length == 2) {
                searchStr = subs[0].trim();
                radiusStr = subs[1].trim().isEmpty() ? radiusStr : subs[1].trim();
                try{
                    radius = Double.valueOf(radiusStr);
                }catch (Exception e) {
                    System.out.println("parse search string for radius line 121 " + e.getMessage());
                }

            }
            final String tenantId = (String)UI.getCurrent().getSession().getAttribute(LoginDataSource.TENANT);
            GeoApiContext context;
            GeoApiContext.Builder builder = new GeoApiContext.Builder();
            builder.apiKey(MainView.API_KEY);
            context = builder.build();
            TextSearchRequest textSearchRequest = PlacesApi.textSearchQuery(context, searchStr);
            textSearchRequest.setCallback(new PendingResult.Callback<PlacesSearchResponse>() {

                //if value comes from google
                @Override
                public void onResult(PlacesSearchResponse placesSearchResponse) {
                    double lat = placesSearchResponse.results[0].geometry.location.lat;
                    double lon = placesSearchResponse.results[0].geometry.location.lng;
                    WGS84Point point = getPoint(lat, lon);
                    String geoHash = GeoHash.geoHashStringWithCharacterPrecision(point.getLatitude(), point.getLongitude(), 12);

                    getUI().get().access(() -> {
                        /*gm.removeAll();
                        MapBounds mb = new MapBounds();
                        gm.addMarker(JsonConverter.createMarker("1" , "center" , lat , lon));
                        mb.add(new LatLon(lat, lon));
                        gm.zoomToBounds(JsonConverter.createBounds(mb));
                        gm.setZoomLevel(calculateZoom(radius));*/
                       // testPull(geoHash, radius);
                        testPull(tenantId, geoHash, radius);
                    });

                }

                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println(throwable);
                }
            });

        });

       /* //Tests:
        //Find the lat lon from the search reasult: top placess:
        WGS84Point point = getPoint(1.00, 87.00);
        String geoHash = "wh0r92k4dzn7";//GeoHash.geoHashStringWithCharacterPrecision(point.getLatitude(), point.getLongitude(), 12);
        //WGS84Point gh = GeoHash.fromGeohashString(geoHash).getPoint();
        testPull(geoHash, 10000.0);
        //*/
    }

    public WGS84Point getPoint(double lat, double lon) {
        return new WGS84Point(Double.valueOf(lat), Double.valueOf(lon));
    }

    private void testPull(String tenantId, String hash, double radius) {
        //
        GeoEventFilter filter = new GeoEventFilter();
        filter.setTenantID(tenantId);
        //filter.setUserID(response.getUsername());
        //
        SearchQuery query = SearchQuery.createQuery(SearchQuery.class, 1, SortOrder.ASE);
        query.add(GeoEventFilter.SEARCH_GEO_HASH).isEqualTo(hash);
        query.add(GeoEventFilter.SEARCH_CIRCLE_RADIUS).isEqualTo(radius);
        filter.setQuery(query);
        //Registration for listeners:
        String topicToListen = createTopic(filter, "/consume" + "/" + "search");
        consumerSocket = createAndConnectConsumerSocket();
        consumerSocket.unsubscribe(topicToListen);
        consumerSocket.subscribe(topicToListen, Message.class, message -> {

            GeoTrackingEvent event = (GeoTrackingEvent) message.getEvent(GeoTrackingEvent.class);
            if (event.getEventType() == EventType.START
                    || event.getEventType() == EventType.RESUME) {
                //Started || Resumed
                System.out.println(event.toString());
                GeoTrackerInfo tracker = new GeoTrackerInfo();
                tracker.setTrackID(event.getTrackID());
                tracker.setUserID(event.getUserID());
                tracker.setTenantID(event.getTenantID());
                createMapMarker(tracker);
                //
                drawMarkers(event.getLocations());
                    //UI.getCurrent().push();


            } else {
                //ENDs
                System.out.println("STOP :" + event.getTimestamp());
            }

        });
        //Send request to server:
        consumerSocket.send("/search.location", filter);
    }



    private void drawMarkers(List<GeoLocation> locations) {

        System.out.println("Got Locations");
        if (locations.size() <= 0) return;
        GeoLocation location = locations.get(0);
        String geoHash = location.getGeoHash();
        GeoHash geoHash1 = GeoHash.fromGeohashString(geoHash);
        String trackID = location.getTrackID();

        try{
            //TODO:impose all these conditions in the commented code

            UI.getCurrent().access( ()-> {
                gm.addMarker(JsonConverter.
                        createMarker(trackID , location.getTenantID() , geoHash1.getPoint().getLatitude() , geoHash1.getPoint().getLongitude()));
            });




            /*GoogleMapMarker marker = trackerMarkers.get(trackID);
            if(marker != null) googleMap.removeMarker(marker);
            marker = new GoogleMapMarker(markerCaption.get(trackID)
                    , new LatLon(geoHash1.getPoint().getLatitude(), geoHash1.getPoint().getLongitude())
                    , false
                    , "VAADIN/" + markerIcons.get(trackID));
            trackerMarkers.put(trackID, marker);
            marker.setAnimationEnabled(false);
            googleMap.addMarker(marker);*/
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void createMapMarker(GeoTrackerInfo tracker) {
        //TODO:
        System.out.println("Drawing Merkars");

    }

    private MessagingTemplate consumerSocket = null;

    private MessagingTemplate createAndConnectConsumerSocket() {
        if (this.consumerSocket != null) return this.consumerSocket;
        try {
            MessagingTemplate consumerSocket = Interactor.create(MessagingTemplate.class, SocketType.Standard);
            AuthResponse response = (AuthResponse) UI.getCurrent().getSession().getAttribute(LoginDataSource.AUTH_RESPONSE);
            consumerSocket.setAuthorizationHeader(response.getAccessToken());
            consumerSocket.setQueryParam(APIContext.APPID.name(), response.getTenantID());
            consumerSocket.connectConsumer("/geo-tracker");
            consumerSocket.connectionErrorHandler(exp -> {
                System.out.println("ConsumerSocket Disconnected");
                consumerSocket.disconnect();
                try {
                    System.out.println("ConsumerSocket reconnecting...");
                    consumerSocket.connectTenant("/geo-tracker");
                } catch (Exception e) {
                    //System.out.println(e.getMessage());
                }
            });
            return consumerSocket;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void disconnect() {
        if (this.consumerSocket == null) return;
        this.consumerSocket.disconnect();
    }

    private void startMotion(UI ui) {
        if (motion != null) return;
        this.motion = serviceExecutor.submit(() -> {
            List<LatLon> point = createWayPoint();
            for (LatLon ltn : point) {
                ui.access(() -> {
                    gm.addMarker(JsonConverter.createMarker("s"
                            , "s"
                            , ltn.getLat()
                            , ltn.getLon()));
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.motion = null;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        gm.addMarker(JsonConverter.createMarker("ID0", "Sample marker", 23.7806365, 90.4193257));
//        MapBounds bounds = new MapBounds();
//        bounds.add(new LatLon(23, 90));
//        bounds.add(new LatLon(25, 91));
//        gm.zoomToBounds(JsonConverter.createBounds(bounds));


        //gm.addMarker(JsonConverter.createMarker("ID2", "Sample marker 2", 23.8506365, 90.4193257));
        // gm.setZoomLevel(8);
        // gm.zoomToBounds(JsonConverter.createBounds(bounds));
    }

    public void addMarker() {
        this.gm.addMarker(JsonConverter.createMarker("02", "Sayed Marker", 23.8506365, 90.4193257));
        this.gm.addPolyLine(JsonConverter.createPolyLine("path1", createWayPoint()));
    }

    private List<LatLon> createWayPoint() {

        List<LatLon> latLons = new ArrayList<>();
        LatLon address = new LatLon(23.8906365, 95.7893257);
        latLons.add(address);
        address = new LatLon(21.8506365, 92.4193257);
        latLons.add(address);
        address = new LatLon(22.8506365, 98.4593257);
        latLons.add(address);
        address = new LatLon(24.8606365, 94.4693257);
        latLons.add(address);
        address = new LatLon(28.8706365, 90.4793257);
        latLons.add(address);
        address = new LatLon(25.8806365, 91.4893257);
        latLons.add(address);
        address = new LatLon(23.8906365, 93.4993257);
        latLons.add(address);
        address = new LatLon(26.8906365, 90.7893257);
        latLons.add(address);

        return latLons;
    }


    public static int getRandomIntegerBetweenRange(int min, int max) {
        Random random = new Random();
        return random.ints(min, (max + 1)).findFirst().getAsInt();
    }

    private int calculateZoom(double radious) {
        if (radious >= 10000) return 13;
        if (radious < 10000 && radious >= 5000) return 14;
        if (radious < 5000 && radious >= 2000) return 14;
        if (radious < 2000 && radious >= 1000) return 15;
        if (radious < 1000 && radious >= 500) return 15;
        return 16;
    }

    private String createTopic(GeoEventFilter filter, String path) {
        String topicToListen = path;
        if (filter.getTenantID() != null) {
            topicToListen += "/" + filter.getTenantID();
            if (filter.getUserID() != null) {
                topicToListen += "/" + filter.getUserID();
                if (filter.getTrackID() != null) {
                    topicToListen += "/" + filter.getTrackID();
                }
            }
        }
        return topicToListen;
    }


}
