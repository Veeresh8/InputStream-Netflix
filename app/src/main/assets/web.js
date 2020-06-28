(function() {
    var currentUrl = window.location.href;
    setInterval(function() {
        if (currentUrl != window.location.href) {
            console.log("RAVE: Page changed")
            currentUrl = window.location.href;
            window.JSInterface.pageChanged(window.location.href);
        }
    }, 100);

    setInterval(function() {
        console.log("RAVE: Re-hooking elements");
        hookElements();
    }, 1000);

    console.log("RAVE: Hooking elements");
    hookElements();

    function hookElements() {
        var videos = document.getElementsByTagName('video');
        console.log("RAVE: Videos 1", videos);
        for (i = 0; i < videos.length; i++) {
            overrideElement(videos[i]);
        }

        var audios = document.getElementsByTagName('audio');
        console.log("RAVE: Audios 1", audios);
        for (i = 0; i < audios.length; i++) {
            overrideElement(audios[i]);
        }

        var iframes = document.getElementsByTagName('iframe');
        for (i = 0; i < iframes.length; i++) {
            var iframeDocument = iframes[i].contentDocument || iframes[i].contentWindow.document;

            var videos = iframeDocument.getElementsByTagName('video');
            console.log("RAVE: Videos 2", videos);
            for (j = 0; j < videos.length; j++) {
                overrideElement(videos[j]);
            }
            var audios = iframeDocument.getElementsByTagName('audio');
            console.log("RAVE: Audios 2", audios);
            for (j = 0; j < audios.length; j++) {
                overrideElement(audios[j]);
            }
        }
    }

    function overrideElement(element) {
    console.log("RAVE: overrideElement 1 - " + element);
        if(element.hasAttribute("raveHooked")) {
            return;
        }

        console.log("RAVE: overrideElement 2 - " + element);
        element.pause();
        element.autoplay = false;
        element.muted = true;
        element.addEventListener("click", onClick);
        element.addEventListener("play", onPlay);
        element.setAttribute("raveHooked", "raveHooked");
    }

    function onClick(e) {
        console.log("RAVE: Clicked");
        e.preventDefault();
        e.stopPropagation();
        e.target.pause();
        startVideo();
        return false;
    }

    function onPlay(e) {
        console.log("RAVE: Played");
        e.preventDefault();
        e.stopPropagation();
        e.target.pause();
        startVideo();
        return false;
    }

    function startVideo() {
        console.log("RAVE: Start video");
        window.JSInterface.startVideo();
    }
})();