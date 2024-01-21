function extractVideoCoverFromUrl(url) {
    let videoId = null;
    console.log("VIDEO ID URL:"+ url);
    try {
        const youtubeRegex = /^(?:https?:\/\/)?(?:www\.)?(?:youtube\.com\/(?:[^\/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?\/\s]{11})/i;

        // Regular expression to match YouTube Music URLs
        const youtubeMusicRegex = /^(?:https?:\/\/)?music\.youtube\.com\/(?:watch\?v=|embed\/|.*[?&]v=)([^"&?\/\s]{11})/i;

        // Try matching against both YouTube and YouTube Music URL patterns
        const youtubeMatch = url.match(youtubeRegex);
        const youtubeMusicMatch = url.match(youtubeMusicRegex);

        // Extract the video ID if matched
        if (youtubeMatch && youtubeMatch[1]) {
            videoId = youtubeMatch[1];
        } else if (youtubeMusicMatch && youtubeMusicMatch[1]) {
            videoId = youtubeMusicMatch[1];
        }
    }
    catch (e) {
        console.log(e);
        return null;
    }

    return `http://img.youtube.com/vi/${videoId}/maxresdefault.jpg`;
}

function setSliderValue(duration,position){
    var slider = document.getElementById("trackSlider");
    slider.max = duration;
    slider.value = position;



}