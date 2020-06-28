(function() {
    // This function was found here : https://stackoverflow.com/a/16430350
    function closest(el, selector) {
        var matchesSelector = el.matches || el.webkitMatchesSelector || el.mozMatchesSelector || el.msMatchesSelector;
        while (el) {
            if (matchesSelector.call(el, selector)) {
                break;
            }
            el = el.parentElement;
        }
        return el;
    }

    document.onclick = function onClick (e) {
        e = e ||  window.event;
        var element = e.target || e.srcElement;
        window.JSInterface.startVideo(closest(element, "a").href);
        return false;
     };
    window.onunload = function urlChanged (e) {
         window.JSInterface.onUrlChange(window.location.href);
         return false;
      };
})();