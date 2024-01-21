"USE STRICT"
var myCarousel = document.querySelector('#carouselExampleControls')
var playlist_container = document.getElementById("playlists-container");

var carousel = new bootstrap.Carousel(myCarousel)

playlists_list = JSON.parse( document.currentScript.getAttribute('playlists'));

console.log(playlists_list.length);

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
    stompClient.subscribe('/bot/next', nextSong2);

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
function nextSong2(payload){
    var message = payload.body;
    console.log("nextSong2"+message);

}


links = ["http://img.youtube.com/vi/yJpJCZYTL74/maxresdefault.jpg","http://img.youtube.com/vi/qmpORlyvJpo/maxresdefault.jpg","http://img.youtube.com/vi/m1aWkPjA4w0/maxresdefault.jpg","http://img.youtube.com/vi/yJpJCZYTL74/maxresdefault.jpg","http://img.youtube.com/vi/qmpORlyvJpo/maxresdefault.jpg","http://img.youtube.com/vi/m1aWkPjA4w0/maxresdefault.jpg","http://img.youtube.com/vi/yJpJCZYTL74/maxresdefault.jpg","http://img.youtube.com/vi/qmpORlyvJpo/maxresdefault.jpg","http://img.youtube.com/vi/m1aWkPjA4w0/maxresdefault.jpg"]
var index = 0;





function nextSong(){
    index++;
    carousel.next();
    if(index >= links.length){
        index = 0;
    }
    document.body.style.backgroundImage = "url('"+links[index]+"')";
}

function previousSong(){
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

window.onload = function(){
    fillPlaylist();
    connect()
}


