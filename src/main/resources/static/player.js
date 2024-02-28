"USE STRICT"
var myCarousel = document.querySelector('#carouselExampleControls')
var playlist_container = document.getElementById("playlists-container");
var tracks_container = document.getElementById("tracks-container");
var carousel = new bootstrap.Carousel(myCarousel)
var slider = document.getElementById("trackSlider");
var playPauseButton = document.getElementById("playPauseIcon");
const shuffleIcon = document.getElementById("shuffleIcon");
playlists_list = JSON.parse( document.currentScript.getAttribute('playlists'));
tracks_list = JSON.parse( document.currentScript.getAttribute('tracks'));
soundpad_list = JSON.parse( document.currentScript.getAttribute('soundpadFiles'));
var ispaused = false;
var lastPosition = 0;



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
    console.log("onCurrentSongMessageReceived");
    // update playPauseButton depending on position of the song comparing to last position
    if (currentTrack.position == lastPosition){
        playPauseButton.className = "fa fa-play";
    } else {
        playPauseButton.className = "fa fa-pause";
    }
    lastPosition = currentTrack.position;

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








function nextSong(){
    stompClient.send('/app/bot/song.changeNext');
}

function clearList(){
    stompClient.send('/app/bot/playlist.clear');
    // clear the list
    tracks_container.innerHTML = "";
    playPauseButton.className = "fa fa-play";

}
function playPause(){
    stompClient.send('/app/bot/song.playPause');

   playPauseButton.className = ispaused ? "fa fa-pause" : "fa fa-play";
    ispaused = !ispaused;


}

function playPlaylist(playlistUrl){
    stompClient.send('/app/bot/playlist.play', {}, JSON.stringify({ playlistUrl: playlistUrl }));
    shake(document.getElementById(playlistUrl))
}

function shuffle(){
    stompClient.send('/app/bot/playlist.shuffle');
    shake(shuffleIcon);
    shake(tracks_container);
}

function fillPlaylist(){

    var counter =0;
    for(var i = 0; i < playlists_list.length; i++){

        if (counter == 0){
            var newPlaylist = document.createElement("div");
            newPlaylist.className = "row";

        }
        counter++;

        var newCard = document.createElement("div");
        newCard.id = playlists_list[i]["url"];
        newCard.onclick = function(){playPlaylist(this.id)};
        newCard.className = "col";
        newCard.innerHTML = `

        <div class="playlist-card-container card" >
                <img class="playlist-card-image" src="`+extractVideoCoverFromUrl(playlists_list[i]["tracks"][0]["url"])+`" alt="..." >
                <div class="playlist-card-nav">
                   <a style="text-decoration:none; color:inherit" href="`+ playlists_list[i]['url'] +` "> <i class="fa fa-link playlist-icons" style="margin: 1rem;"></i> </a>
                </div>
                <div class="playlist-card-button">
                   <button class="playlist-icons" style="background: none; border:none"> <i class="material-icons playlist-icons" style="font-size:3rem" >play_circle</i> </button>
                </div>
                <div class="playlist-card-title"><p > ${playlists_list[i]["name"]}</p></div>

        </div>
        `
        newPlaylist.appendChild(newCard);

        if (counter == 4){

            counter = 0;
            playlist_container.appendChild(newPlaylist);
        }
    }



    if (counter != 0){
        // if a row is not complete, add empty columns
        for (var i = counter; i < 4; i++){

            var newCard = document.createElement("div");
            newCard.className = "col";
            newCard.innerHTML = `
            <div style="width: 18rem; background-color: transparent;" ></div>
                       `
            newPlaylist.appendChild(newCard);
        }
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

function fillSoundpad(){

    var soundpad_container = document.getElementById("soundpad-container");
    soundpad_container.innerHTML = "";
    var soundpad_row = document.createElement("div");
    soundpad_row.className = "row soundpad-row";
    var counter = 0;
    var rowCounter = 0;
    for (var i = 0; i < soundpad_list.length; i++) {
        if (counter == 0) {
            soundpad_row = document.createElement("div");
            soundpad_row.className = "row soundpad-row";
        }
        counter++;
        var soundpad_col = document.createElement("div");
        soundpad_col.className = "col soundpad-col";
        var soundpad_button = document.createElement("button");
        soundpad_button.className = "soundpad-button";
        soundpad_button.onclick = function () {
            playSoundpad(this.id)
        };
        soundpad_button.id = soundpad_list[i];
        soundpad_button.innerHTML = soundpad_list[i].split(".")[0] + `<i class="fa fa-play soundpad-icon"></i>`;
        soundpad_col.appendChild(soundpad_button);
        soundpad_row.appendChild(soundpad_col);
        if (counter == 4) {
            rowCounter++;
            counter = 0;
            soundpad_container.appendChild(soundpad_row);
        }
    }
    if (counter != 0) {
        // if a row is not complete, add empty columns
        for (var i = counter; i < 4; i++) {
            var soundpad_col = document.createElement("div");
            soundpad_col.className = "col soundpad-col";
            soundpad_col.innerHTML = `
            <div style="width: 100%; background-color: transparent;" ></div>
                       `
            soundpad_row.appendChild(soundpad_col);
        }
        soundpad_container.appendChild(soundpad_row);
    }

    // extend the soundpad container to the bottom of the page
    const ofcanvasTop = document.getElementById("offcanvasTop");

    ofcanvasTop.style.height = Math.max(soundpad_container.offsetHeight*(rowCounter+1),200)    + "px";
    console.log(soundpad_container.offsetHeight);


}
function playSoundpad(soundpadId) {
    stompClient.send('/app/bot/soundpad.play', {}, JSON.stringify({ soundpadId: soundpadId }));
    shake(document.getElementById(soundpadId));
}

function playTrack(trackId) {
    try {
        stompClient.send('/app/bot/song.changeById', {}, JSON.stringify({ trackId: trackId }));
    } catch (error) {
        console.error("Error sending message to WebSocket server:", error);
        // Handle the error, show an alert, or log it as needed
    }
}

window.onload = function(){
    saveTokenFromUrl();
    fillPlaylist();
    fillTracks();
    connect();
    fillSoundpad();
}


