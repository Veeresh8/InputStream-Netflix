package tv.danmaku.ijk.media.example.netflix;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;

import java.util.concurrent.TimeoutException;

public class EglCore {
    private static final int EGL_RECORDABLE_ANDROID = 12610;
    public static final int FLAG_RECORDABLE = 1;
    private static final String LOG_TAG = "EglCore";
    private EGLConfig eglConfig = null;
    private EGLContext eglContext = EGL14.EGL_NO_CONTEXT;
    private EGLDisplay eglDisplay = EGL14.EGL_NO_DISPLAY;

    public EglCore() {
        EGLContext eGLContext = EGL14.EGL_NO_CONTEXT;
        EGLDisplay eglGetDisplay = EGL14.eglGetDisplay(0);
        this.eglDisplay = eglGetDisplay;
        int[] iArr = new int[2];
        EGL14.eglInitialize(eglGetDisplay, iArr, 0, iArr, 1);
        if (this.eglContext == EGL14.EGL_NO_CONTEXT) {
            EGLConfig config = getConfig(0, 2);
            if (config != null) {
                EGLContext eglCreateContext = EGL14.eglCreateContext(this.eglDisplay, config, eGLContext, new int[]{12440, 2, 12344}, 0);
                this.eglConfig = config;
                this.eglContext = eglCreateContext;
            } else {
                throw new RuntimeException("Unable to find a suitable EGLConfig");
            }
        }
        int[] iArr2 = new int[1];
        EGL14.eglQueryContext(this.eglDisplay, this.eglContext, 12440, iArr2, 0);
        StringBuilder sb = new StringBuilder();
        sb.append("EGLContext created, client version ");
        sb.append(iArr2[0]);

    }

    private EGLConfig getConfig(int i, int i2) {
        int[] iArr = {12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, i2 >= 3 ? 68 : 4, 12344, 0, 12344};
        if ((i & 1) != 0) {
            iArr[10] = EGL_RECORDABLE_ANDROID;
            iArr[11] = 1;
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (EGL14.eglChooseConfig(this.eglDisplay, iArr, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
            return eGLConfigArr[0];
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unable to find RGB8888 / ");
        sb.append(i2);
        sb.append(" EGLConfig");

        return null;
    }

    public int querySurface(EGLSurface eGLSurface, int i) {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(this.eglDisplay, eGLSurface, i, iArr, 0);
        return iArr[0];
    }

    public void release() throws TimeoutException {
        if (this.eglDisplay != EGL14.EGL_NO_DISPLAY) {
            EGL14.eglMakeCurrent(this.eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroyContext(this.eglDisplay, this.eglContext);
            EGL14.eglReleaseThread();
            EGL14.eglTerminate(this.eglDisplay);
        }
        this.eglDisplay = EGL14.EGL_NO_DISPLAY;
        this.eglContext = EGL14.EGL_NO_CONTEXT;
        this.eglConfig = null;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (this.eglDisplay != EGL14.EGL_NO_DISPLAY) {
                release();
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            super.finalize();
            throw th;
        }
        super.finalize();
    }

    public void releaseSurface(EGLSurface eGLSurface) {
        EGL14.eglDestroySurface(this.eglDisplay, eGLSurface);
    }

    public EGLSurface createWindowSurface(Object obj) {
        return EGL14.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, obj, new int[]{12344}, 0);
    }

    public void makeCurrent(EGLSurface eGLSurface) {
        EGL14.eglMakeCurrent(this.eglDisplay, eGLSurface, eGLSurface, this.eglContext);
    }

    public void makeNothingCurrent() {
        EGL14.eglMakeCurrent(this.eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
    }

    public boolean swapBuffers(EGLSurface eGLSurface) {
        return EGL14.eglSwapBuffers(this.eglDisplay, eGLSurface);
    }
}
