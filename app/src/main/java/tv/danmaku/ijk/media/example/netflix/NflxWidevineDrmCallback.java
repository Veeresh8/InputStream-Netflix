package tv.danmaku.ijk.media.example.netflix;


import com.google.android.exoplayer.drm.ExoMediaDrm;
import com.google.android.exoplayer.drm.MediaDrmCallback;

import java.util.UUID;

public class NflxWidevineDrmCallback implements MediaDrmCallback {

    @Override
    public byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest provisionRequest) throws Exception {
        return MslNativeSession.getInstance().doWidevineProvisioning(provisionRequest.getDefaultUrl(), provisionRequest.getData());
    }

    @Override
    public byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest request) throws Exception {
        return MslNativeSession.getInstance().doWidevineDrm(request.getData());
    }
}
