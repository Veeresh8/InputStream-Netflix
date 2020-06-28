package tv.danmaku.ijk.media.example.netflix;

import android.content.Context;
import android.opengl.GLES20;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ShaderSurfaceView extends SurfaceView {
    private static final String LOG_TAG = ShaderSurfaceView.class.getSimpleName();
    private Shader.FragmentShader fragmentShader;
    private int mvpMatrixHandle;
    private int positionHandle;
    private int programId;
    private int stMatrixHandle;
    private boolean stopRendering = false;
    private int textureHandle;
    private final ConcurrentHashMap<String, Float> varsToVals = new ConcurrentHashMap<>();
    private Shader.VertexShader vertexShader;
    private WindowSurface windowSurface;

    public ShaderSurfaceView(Context context) {
        super(context);
    }

    public ShaderSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setResolution(int i, int i2) {
        getHolder().setFixedSize(i, i2);
    }

    public void init(Shader.VertexShader vertexShader2, Shader.FragmentShader fragmentShader2) {
        this.vertexShader = vertexShader2;
        this.fragmentShader = fragmentShader2;
    }

    public void setShaderVariable(String str, float f) {
        this.varsToVals.put(str, Float.valueOf(f));
        String str2 = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Setting ");
        sb.append(str);
        sb.append(" to ");
        sb.append(f);
    }

    public boolean containsShaderVariable(String str) {
        return this.varsToVals.containsKey(str);
    }

    public float getShaderVariable(String str) {
        return ((Float) this.varsToVals.get(str)).floatValue();
    }

    public ConcurrentMap<String, Float> getVarMap() {
        return this.varsToVals;
    }

    public void setStopRendering(boolean z) {
        this.stopRendering = z;
    }

    public boolean getStopRendering() {
        return this.stopRendering;
    }

    public int getProgramId() {
        return this.programId;
    }

    public int getPositionHandle() {
        return this.positionHandle;
    }

    public int getTextureHandle() {
        return this.textureHandle;
    }

    public int getMvpMatrixHandle() {
        return this.mvpMatrixHandle;
    }

    public int getStMatrixHandle() {
        return this.stMatrixHandle;
    }

    public void setWindowSurface(WindowSurface windowSurface2) {
        this.windowSurface = windowSurface2;
    }

    public WindowSurface getWindowSurface() {
        return this.windowSurface;
    }

    public void releaseSurface() {
        WindowSurface windowSurface2 = this.windowSurface;
        if (windowSurface2 != null) {
            windowSurface2.release();
            this.windowSurface = null;
        }
    }

    public void createProgram() {
        int loadShader = loadShader(35633, this.vertexShader.getVertexShaderString());
        if (loadShader != 0) {
            int loadShader2 = loadShader(35632, this.fragmentShader.getFragmentShaderString());
            if (loadShader2 != 0) {
                int glCreateProgram = GLES20.glCreateProgram();
                if (glCreateProgram != 0) {
                    GLES20.glAttachShader(glCreateProgram, loadShader);
                    GLES20.glAttachShader(glCreateProgram, loadShader2);
                    GLES20.glLinkProgram(glCreateProgram);
                    int[] iArr = new int[1];
                    GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
                    if (iArr[0] == 1) {
                        this.programId = glCreateProgram;
                    } else {
                        GLES20.glDeleteProgram(glCreateProgram);
                    }
                }
            }
        }
    }

    private int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        if (glCreateShader == 0) {
            return glCreateShader;
        }
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    public void setupShaderVars() {
        this.positionHandle = GLES20.glGetAttribLocation(this.programId, "aPosition");
        this.textureHandle = GLES20.glGetAttribLocation(this.programId, "aTextureCoord");
        this.mvpMatrixHandle = GLES20.glGetUniformLocation(this.programId, "uMVPMatrix");
        this.stMatrixHandle = GLES20.glGetUniformLocation(this.programId, "uSTMatrix");
    }
}
