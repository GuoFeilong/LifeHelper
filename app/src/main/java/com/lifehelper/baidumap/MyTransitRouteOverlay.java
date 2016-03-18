package com.lifehelper.baidumap;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.lifehelper.R;

/**
 * Created by jsion on 16/3/18.
 */
public class MyTransitRouteOverlay extends TransitRouteOverlay {
    private boolean useDefaultIcon;

    public MyTransitRouteOverlay(BaiduMap baiduMap, boolean useDefaultIcon) {
        super(baiduMap);
        this.useDefaultIcon = useDefaultIcon;
    }

    @Override
    public BitmapDescriptor getStartMarker() {
        if (useDefaultIcon) {
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_st);
        }
        return null;
    }

    @Override
    public BitmapDescriptor getTerminalMarker() {
        if (useDefaultIcon) {
            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_en);
        }
        return null;
    }
}
