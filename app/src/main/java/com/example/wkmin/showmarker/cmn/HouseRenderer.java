package com.example.wkmin.showmarker.cmn;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by wkmin on 2017. 5. 12..
 *
 */

public class HouseRenderer extends DefaultClusterRenderer<HouseClusterItem> {
    private final IconGenerator mClusterIconGenerator;

    public HouseRenderer(Context context, GoogleMap map, ClusterManager<HouseClusterItem> clusterManager) {
        super(context, map, clusterManager);
        mClusterIconGenerator = new IconGenerator(context);
    }

    @Override
    protected void onClusterItemRendered(HouseClusterItem clusterItem, final Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(clusterItem.getIconUrl(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(loadedImage));
            }
        });
    }

    @Override
    protected void onBeforeClusterItemRendered(HouseClusterItem item, final MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<HouseClusterItem> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
        Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<HouseClusterItem> cluster) {
        return super.shouldRenderAsCluster(cluster);
    }

    @Override
    public Marker getMarker(HouseClusterItem clusterItem) {
        return super.getMarker(clusterItem);
    }
}
