package com.android.openglsample.app;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by ericson on 2016/1/2 0002.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private MyRender render;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        render = new MyRender();
        setRenderer(render);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
