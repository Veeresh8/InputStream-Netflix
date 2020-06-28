package tv.danmaku.ijk.media.example.netflix;

import android.opengl.EGL14;
import android.opengl.EGLSurface;
import android.view.Surface;

public class WindowSurface {

    private EglCore eglCore;
    private EGLSurface eglSurface = EGL14.EGL_NO_SURFACE;
    private boolean releaseSurface;
    private Surface surface;

    public WindowSurface(EglCore eglCore2, Surface surface2, boolean z) {
        this.eglCore = eglCore2;
        this.eglSurface = eglCore2.createWindowSurface(surface2);
        this.surface = surface2;
        this.releaseSurface = z;
    }

    public void makeCurrent() {
        this.eglCore.makeCurrent(this.eglSurface);
    }

    public void release() {
        this.eglCore.releaseSurface(this.eglSurface);
        this.eglSurface = EGL14.EGL_NO_SURFACE;
        Surface surface2 = this.surface;
        if (surface2 != null) {
            if (this.releaseSurface) {
                surface2.release();
            }
            this.surface = null;
        }
    }

    public int getWidth() {
        return this.eglCore.querySurface(this.eglSurface, 12375);
    }

    public int getHeight() {
        return this.eglCore.querySurface(this.eglSurface, 12374);
    }

    public boolean swapBuffers() {
        return this.eglCore.swapBuffers(this.eglSurface);
    }
}
