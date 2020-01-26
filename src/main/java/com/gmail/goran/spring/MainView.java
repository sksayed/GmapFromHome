package com.gmail.goran.spring;

import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.PlacesApi;
import com.google.maps.TextSearchRequest;
import com.google.maps.model.PlacesSearchResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Push
@Route("")
public class MainView extends VerticalLayout {

    HorizontalLayout searchLayout;
    Button searchButton;
    TextField searchField;
    private ExecutorService serviceExecutor = Executors.newSingleThreadExecutor();
    private Future motion;
    private int i = 0;

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
        this.searchButton = new Button("Search");
        this.searchField = new TextField();
        searchLayout.add(searchField, searchButton);
        add(searchLayout, gm, showMotion);
        //addMarker();
    }

    private void initListeners() {


        this.showMotion.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                startMotion(UI.getCurrent());
            }
        });

        this.searchButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                String searchText = searchField.getValue();

                GeoApiContext context;
                GeoApiContext.Builder builder = new GeoApiContext.Builder();
                builder.apiKey("AIzaSyA-ILRTlV8Gd-GvTXEP1oUQej4Q9ZDhe1k");
                context = builder.build();
                TextSearchRequest textSearchRequest = PlacesApi.textSearchQuery(context, searchText);
                textSearchRequest.setCallback(new PendingResult.Callback<PlacesSearchResponse>() {
                    @Override
                    public void onResult(PlacesSearchResponse placesSearchResponse) {
                        double lat = placesSearchResponse.results[0].geometry.location.lat;
                        double lon = placesSearchResponse.results[0].geometry.location.lng;

                        getUI().get().access(() -> {
                            gm.removeAll();
                            gm.addMarker(JsonConverter.createMarker("y", "jasdasdasd", lat, lon));
                            MapBounds mb = new MapBounds();
                            mb.add(new LatLon(lat, lon));
                            gm.zoomToBounds(JsonConverter.createBounds(mb));
                           // gm.setZoomLevel(15);
                            UI.getCurrent().getUI().get().push();
                        });

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable);
                    }
                });


            }
        });

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


}
