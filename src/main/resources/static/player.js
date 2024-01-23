"USE STRICT"
var myCarousel = document.querySelector('#carouselExampleControls')
var playlist_container = document.getElementById("playlists-container");
var tracks_container = document.getElementById("tracks-container");
var carousel = new bootstrap.Carousel(myCarousel)
var slider = document.getElementById("trackSlider");
playlists_list = JSON.parse( document.currentScript.getAttribute('playlists'));
tracks_list = JSON.parse( document.currentScript.getAttribute('tracks'));


// ************** Websocket ***************

var stompClient = null;

function connect(event) {

    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected );
    console.log("Connected to websocket");
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/bot/currentSong', onCurrentSongMessageReceived);
    stompClient.subscribe('/bot/queue', onQueueMessageReceived);

    console.log("Subscribed to /bot/public");
}

function onCurrentSongMessageReceived(payload) {
    var currentTrack = JSON.parse(payload.body);

    // Update Slider
    setSliderValue(currentTrack.duration,currentTrack.position);
    // Update body background
    document.body.style.backgroundImage = "url('"+extractVideoCoverFromUrl(currentTrack.url)+"')";
    // Update carousel
    carousel._element.querySelector('.carousel-item:first-child img').src = extractVideoCoverFromUrl(currentTrack.url);
    // Update title
    document.getElementById("current-song-title").innerHTML = currentTrack.title;



}

// detect when the slider is moved
slider.oninput = function() {
    // send the new position to the server
    stompClient.send('/app/bot/song.seekPosition', {}, JSON.stringify({ position: this.value }));
}
function onQueueMessageReceived(payload){
    console.log("onQueueMessageReceived");
    tracks_list = JSON.parse(payload.body);
    tracks_container.innerHTML = "";
    fillTracks();

}


links = ["http://img.youtube.com/vi/yJpJCZYTL74/maxresdefault.jpg","http://img.youtube.com/vi/qmpORlyvJpo/maxresdefault.jpg","http://img.youtube.com/vi/m1aWkPjA4w0/maxresdefault.jpg","http://img.youtube.com/vi/yJpJCZYTL74/maxresdefault.jpg","http://img.youtube.com/vi/qmpORlyvJpo/maxresdefault.jpg","http://img.youtube.com/vi/m1aWkPjA4w0/maxresdefault.jpg","http://img.youtube.com/vi/yJpJCZYTL74/maxresdefault.jpg","http://img.youtube.com/vi/qmpORlyvJpo/maxresdefault.jpg","http://img.youtube.com/vi/m1aWkPjA4w0/maxresdefault.jpg"]
var index = 0;





function nextSong(){
    stompClient.send('/app/bot/song.changeNext');

}

function previousSong(){
    // probably impossible because of lavaplayer queue system
    index--;
    carousel.prev();
    if(index < 0){
        index = links.length-1;
    }
    document.body.style.backgroundImage = "url('"+links[index]+"')";
}

function fillPlaylist(){
    console.log("fillPlaylist");
    var counter =0;
    for(var i = 0; i < playlists_list.length; i++){
        if (counter == 0){
            var newPlaylist = document.createElement("div");
            newPlaylist.className = "row";
            counter++;
        }
        var newCard = document.createElement("div");
        newCard.className = "col";
        newCard.innerHTML = `
        <div class="card" style="width: 18rem; margin-top:1rem;">
            <img src="`+extractVideoCoverFromUrl(playlists_list[i]["tracks"][0]["url"])+`" class="card-img-top" alt="...">
            <div class="card-body">
                <h5 class="card-title"> ${playlists_list[i]["name"]} </h5>
                <h5 class="card-title"> ${playlists_list[i]["tracks"].length } Tracks </h5>
            </div>
        </div>
        `
        newPlaylist.appendChild(newCard);
        if (counter == 4){
            counter = 0;
            playlist_container.appendChild(newPlaylist);
        }
    }
    if (counter != 0){


        playlist_container.appendChild(newPlaylist);
    }
}

function fillTracks(){

    for(var i = 0; i < tracks_list.length; i++) {
        var newTrack = document.createElement("li");
        newTrack.id = i;

        newTrack.className = "list-group-item";
        newTrack.onclick = function(){playTrack(this.id)};
        newTrack.innerHTML = `
        <div class="list-item">
            <img src="` + extractVideoCoverFromUrl(tracks_list[i]["url"]) + `" class="list-item-cover" alt="...">
            <div class="col " style="padding: 0;">
                <h1 class="list-item-title"> ${tracks_list[i]["title"]} </h1>
                <h1 class="list-item-duration"> ${ getReadableDurationFromMilliseconds(tracks_list[i]["duration"])} </h1>
            </div>
            <i class="fa fa-music now-playing-icon"></i>
        </div>
        `
        tracks_container.appendChild(newTrack);
    }

}

function playTrack(trackId) {
    try {
        console.log("Playing track: " + trackId);
        stompClient.send('/app/bot/song.changeById', {}, JSON.stringify({ trackId: trackId }));
    } catch (error) {
        console.error("Error sending message to WebSocket server:", error);
        // Handle the error, show an alert, or log it as needed
    }
}

window.onload = function(){
    fillPlaylist();
    fillTracks();
    connect();
}


