"USE STRICT"
var myCarousel = document.querySelector('#carouselExampleControls')
var playlist = document.getElementById("playlists-container");

var carousel = new bootstrap.Carousel(myCarousel)



links = ["http://img.youtube.com/vi/yJpJCZYTL74/maxresdefault.jpg","http://img.youtube.com/vi/qmpORlyvJpo/maxresdefault.jpg","http://img.youtube.com/vi/m1aWkPjA4w0/maxresdefault.jpg","http://img.youtube.com/vi/yJpJCZYTL74/maxresdefault.jpg","http://img.youtube.com/vi/qmpORlyvJpo/maxresdefault.jpg","http://img.youtube.com/vi/m1aWkPjA4w0/maxresdefault.jpg","http://img.youtube.com/vi/yJpJCZYTL74/maxresdefault.jpg","http://img.youtube.com/vi/qmpORlyvJpo/maxresdefault.jpg","http://img.youtube.com/vi/m1aWkPjA4w0/maxresdefault.jpg"]
var index = 0;
console.log("player.js");




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
    for(var i = 0; i < links.length; i++){
        if (counter == 0){
            var newPlaylist = document.createElement("div");
            newPlaylist.className = "row";
            counter++;
        }
        var newCard = document.createElement("div");
        newCard.className = "col";
        newCard.innerHTML = `
        <div class="card" style="width: 18rem; margin-top:1rem;">
            <img src="`+links[i]+`" class="card-img-top" alt="...">
            <div class="card-body">
                <h5 class="card-title">Card title</h5>
            </div>
        </div>
        `
        newPlaylist.appendChild(newCard);
        if (counter == 4){
            counter = 0;
            playlist.appendChild(newPlaylist);
        }
    }
    if (counter != 0){


        playlist.appendChild(newPlaylist);
    }
}

window.onload = function(){
    fillPlaylist();
}


