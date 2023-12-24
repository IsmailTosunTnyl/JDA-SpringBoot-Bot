var playlists = JSON.parse(document.currentScript.getAttribute('data-json'));
console.log(playlists);

function extractVideoId(url) {
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





console.log(playlists[0].tracks.length + "**********");
var selectedPlaylist = 0;
var data = {
  "albums" : [ {
    "album_type" : "single",
    "artists" : [ {
      "external_urls" : {
        "spotify" : "https://open.spotify.com/artist/2wE8kzwd3Ej3WDAtBESiQQ"
      },

      "id" : "2wE8kzwd3Ej3WDAtBESiQQ",
      "name" : "Liza Flume",
      "type" : "artist",
      "uri" : "spotify:artist:2wE8kzwd3Ej3WDAtBESiQQ"
    } ],
    "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
    "copyrights" : [ {
      "text" : "2013 Toast Office Records",
      "type" : "C"
    }, {
      "text" : "2013 Toast Office Records",
      "type" : "P"
    } ],
    "external_ids" : {
      "upc" : "859710277116"
    },
    "external_urls" : {
      "spotify" : "https://open.spotify.com/album/1JpBBH0e1ArGNiTyb3D0So"
    },
    "genres" : [ ],
    "href" : "https://api.spotify.com/v1/albums/1JpBBH0e1ArGNiTyb3D0So",
    "id" : "1JpBBH0e1ArGNiTyb3D0So",
    "images" : [ {
      "height" : 640,
      "url" : "https://i.scdn.co/image/73b0538aca92df17a62730b924f1b69f1a4ee7a9",
      "width" : 640
    }, {
      "height" : 300,
      "url" : "https://i.scdn.co/image/e676b3e8f135c8ef23873c4b7260887d155769b1",
      "width" : 300
    }, {
      "height" : 64,
      "url" : "https://i.scdn.co/image/bdb655ce857d9b7da142dd1ab7ba429b8e4f48a3",
      "width" : 64
    } ],
    "label" : "Toast Office Records",
    "name" : "Full Steam Ahead",
    "popularity" : 34,
    "release_date" : "2013-06-19",
    "release_date_precision" : "day",
    "tracks" : {
      "href" : "https://api.spotify.com/v1/albums/1JpBBH0e1ArGNiTyb3D0So/tracks?offset=0&limit=50",
      "items" : [ {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2wE8kzwd3Ej3WDAtBESiQQ"
          },
          "href" : "https://api.spotify.com/v1/artists/2wE8kzwd3Ej3WDAtBESiQQ",
          "id" : "2wE8kzwd3Ej3WDAtBESiQQ",
          "name" : "Liza Flume",
          "type" : "artist",
          "uri" : "spotify:artist:2wE8kzwd3Ej3WDAtBESiQQ"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 321621,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/08Owzf3KnH9jUYK73B1pA2"
        },
        "href" : "https://api.spotify.com/v1/tracks/08Owzf3KnH9jUYK73B1pA2",
        "id" : "08Owzf3KnH9jUYK73B1pA2",
        "name" : "Swords",
        "preview_url" : "https://p.scdn.co/mp3-preview/3051a83b548aa55c1f1368f336108ee90691978a?cid=null",
        "track_number" : 1,
        "type" : "track",
        "uri" : "spotify:track:08Owzf3KnH9jUYK73B1pA2"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2wE8kzwd3Ej3WDAtBESiQQ"
          },
          "href" : "https://api.spotify.com/v1/artists/2wE8kzwd3Ej3WDAtBESiQQ",
          "id" : "2wE8kzwd3Ej3WDAtBESiQQ",
          "name" : "Liza Flume",
          "type" : "artist",
          "uri" : "spotify:artist:2wE8kzwd3Ej3WDAtBESiQQ"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 165937,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/1vFXrUIGswzdMm6RqG1gMM"
        },
        "href" : "https://api.spotify.com/v1/tracks/1vFXrUIGswzdMm6RqG1gMM",
        "id" : "1vFXrUIGswzdMm6RqG1gMM",
        "name" : "Wasting Away",
        "preview_url" : "https://p.scdn.co/mp3-preview/5ab1a3493c3b8fc1af6094dec7832f9d4e44ff7b?cid=null",
        "track_number" : 2,
        "type" : "track",
        "uri" : "spotify:track:1vFXrUIGswzdMm6RqG1gMM"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2wE8kzwd3Ej3WDAtBESiQQ"
          },
          "href" : "https://api.spotify.com/v1/artists/2wE8kzwd3Ej3WDAtBESiQQ",
          "id" : "2wE8kzwd3Ej3WDAtBESiQQ",
          "name" : "Liza Flume",
          "type" : "artist",
          "uri" : "spotify:artist:2wE8kzwd3Ej3WDAtBESiQQ"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 233169,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/3e6i7hbLEnlPXh0KnrR6v8"
        },
        "href" : "https://api.spotify.com/v1/tracks/3e6i7hbLEnlPXh0KnrR6v8",
        "id" : "3e6i7hbLEnlPXh0KnrR6v8",
        "name" : "What We Called Love",
        "preview_url" : "https://p.scdn.co/mp3-preview/41269303fb6a2ac68012ee0c15acbb1e97d0a868?cid=null",
        "track_number" : 3,
        "type" : "track",
        "uri" : "spotify:track:3e6i7hbLEnlPXh0KnrR6v8"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2wE8kzwd3Ej3WDAtBESiQQ"
          },
          "href" : "https://api.spotify.com/v1/artists/2wE8kzwd3Ej3WDAtBESiQQ",
          "id" : "2wE8kzwd3Ej3WDAtBESiQQ",
          "name" : "Liza Flume",
          "type" : "artist",
          "uri" : "spotify:artist:2wE8kzwd3Ej3WDAtBESiQQ"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 189212,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/5DF0mIA710tleo2p8nynnd"
        },
        "href" : "https://api.spotify.com/v1/tracks/5DF0mIA710tleo2p8nynnd",
        "id" : "5DF0mIA710tleo2p8nynnd",
        "name" : "Poison",
        "preview_url" : "https://p.scdn.co/mp3-preview/cdbaa2369b8feaf72965d170e52119fa3c4a1afe?cid=null",
        "track_number" : 4,
        "type" : "track",
        "uri" : "spotify:track:5DF0mIA710tleo2p8nynnd"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2wE8kzwd3Ej3WDAtBESiQQ"
          },
          "href" : "https://api.spotify.com/v1/artists/2wE8kzwd3Ej3WDAtBESiQQ",
          "id" : "2wE8kzwd3Ej3WDAtBESiQQ",
          "name" : "Liza Flume",
          "type" : "artist",
          "uri" : "spotify:artist:2wE8kzwd3Ej3WDAtBESiQQ"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 317680,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/0Kq5myyVxQASxUiKosvMJ7"
        },
        "href" : "https://api.spotify.com/v1/tracks/0Kq5myyVxQASxUiKosvMJ7",
        "id" : "0Kq5myyVxQASxUiKosvMJ7",
        "name" : "In Time",
        "preview_url" : "https://p.scdn.co/mp3-preview/f7d35700667f79c150576943ca6a615a0f173f75?cid=null",
        "track_number" : 5,
        "type" : "track",
        "uri" : "spotify:track:0Kq5myyVxQASxUiKosvMJ7"
      } ],
      "limit" : 50,
      "next" : null,
      "offset" : 0,
      "previous" : null,
      "total" : 5
    },
    "type" : "album",
    "uri" : "spotify:album:1JpBBH0e1ArGNiTyb3D0So"
  }, {
    "album_type" : "single",
    "artists" : [ {
      "external_urls" : {
        "spotify" : "https://open.spotify.com/artist/3fmMaLC5jjf2N4EC2kTx0u"
      },
      "href" : "https://api.spotify.com/v1/artists/3fmMaLC5jjf2N4EC2kTx0u",
      "id" : "3fmMaLC5jjf2N4EC2kTx0u",
      "name" : "Hermitude",
      "type" : "artist",
      "uri" : "spotify:artist:3fmMaLC5jjf2N4EC2kTx0u"
    } ],
    "available_markets" : [ "AD", "AR", "AT", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
    "copyrights" : [ {
      "text" : "2015 Elefant Traks Operations Pty Ltd. under exclusive license to Nettwerk Productions Ltd.",
      "type" : "C"
    }, {
      "text" : "2015 Elefant Traks Operations Pty Ltd. under exclusive license to Nettwerk Productions Ltd.",
      "type" : "P"
    } ],
    "external_ids" : {
      "upc" : "067003860451"
    },
    "external_urls" : {
      "spotify" : "https://open.spotify.com/album/6l7cNe3wCIRqeyZwSQIfzz"
    },
    "genres" : [ ],
    "href" : "https://api.spotify.com/v1/albums/6l7cNe3wCIRqeyZwSQIfzz",
    "id" : "6l7cNe3wCIRqeyZwSQIfzz",
    "images" : [ {
      "height" : 640,
      "url" : "https://i.scdn.co/image/76b24f6dc9b9481a40b0bd2db06a391b6563787f",
      "width" : 640
    }, {
      "height" : 300,
      "url" : "https://i.scdn.co/image/4b837e42244c1838ea57eb52e348388325914991",
      "width" : 300
    }, {
      "height" : 64,
      "url" : "https://i.scdn.co/image/024642df5c5d7b949f8e552680cdcb03eeb8d2ad",
      "width" : 64
    } ],
    "label" : "Nettwerk Records",
    "name" : "HyperParadise [Flume Remix (Ganz Flip)]",
    "popularity" : 43,
    "release_date" : "2014-07-10",
    "release_date_precision" : "day",
    "tracks" : {
      "href" : "https://api.spotify.com/v1/albums/6l7cNe3wCIRqeyZwSQIfzz/tracks?offset=0&limit=50",
      "items" : [ {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/3fmMaLC5jjf2N4EC2kTx0u"
          },
          "href" : "https://api.spotify.com/v1/artists/3fmMaLC5jjf2N4EC2kTx0u",
          "id" : "3fmMaLC5jjf2N4EC2kTx0u",
          "name" : "Hermitude",
          "type" : "artist",
          "uri" : "spotify:artist:3fmMaLC5jjf2N4EC2kTx0u"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 290000,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/0QAjwIEt6FVTbfASxmNolZ"
        },
        "href" : "https://api.spotify.com/v1/tracks/0QAjwIEt6FVTbfASxmNolZ",
        "id" : "0QAjwIEt6FVTbfASxmNolZ",
        "name" : "HyperParadise - Flume Remix (Ganz Flip)",
        "preview_url" : "https://p.scdn.co/mp3-preview/532b6ea8a5e680b6e0db85518633e59357ec3854?cid=null",
        "track_number" : 1,
        "type" : "track",
        "uri" : "spotify:track:0QAjwIEt6FVTbfASxmNolZ"
      } ],
      "limit" : 50,
      "next" : null,
      "offset" : 0,
      "previous" : null,
      "total" : 1
    },
    "type" : "album",
    "uri" : "spotify:album:6l7cNe3wCIRqeyZwSQIfzz"
  }, {
    "album_type" : "album",
    "artists" : [ {
      "external_urls" : {
        "spotify" : "https://open.spotify.com/artist/5sXx89ZqVFOUKTl5g2uiST"
      },
      "href" : "https://api.spotify.com/v1/artists/5sXx89ZqVFOUKTl5g2uiST",
      "id" : "5sXx89ZqVFOUKTl5g2uiST",
      "name" : "Casseurs Flowters",
      "type" : "artist",
      "uri" : "spotify:artist:5sXx89ZqVFOUKTl5g2uiST"
    } ],
    "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
    "copyrights" : [ {
      "text" : "2015 7th Magnitude / 3ème Bureau / Wagram Music",
      "type" : "C"
    }, {
      "text" : "2015 7th Magnitude / 3ème Bureau / Wagram Music",
      "type" : "P"
    } ],
    "external_ids" : {
      "upc" : "3596973343697"
    },
    "external_urls" : {
      "spotify" : "https://open.spotify.com/album/1jfBSA8KgMLol7Iahnq8wv"
    },
    "genres" : [ ],
    "href" : "https://api.spotify.com/v1/albums/1jfBSA8KgMLol7Iahnq8wv",
    "id" : "1jfBSA8KgMLol7Iahnq8wv",
    "images" : [ {
      "height" : 640,
      "url" : "https://i.scdn.co/image/55bebd365262b5b4360c1b227e63c9631a446898",
      "width" : 640
    }, {
      "height" : 300,
      "url" : "https://i.scdn.co/image/6e72d0843dc7245d57ee3520edc7f7c955dafe26",
      "width" : 300
    }, {
      "height" : 64,
      "url" : "https://i.scdn.co/image/780dca84ba43db264fac1150a8fa5a84d7a152a9",
      "width" : 64
    } ],
    "label" : "Cinq 7",
    "name" : "Comment c'est loin",
    "popularity" : 57,
    "release_date" : "2015-12-09",
    "release_date_precision" : "day",
    "tracks" : {
      "href" : "https://api.spotify.com/v1/albums/1jfBSA8KgMLol7Iahnq8wv/tracks?offset=0&limit=50",
      "items" : [ {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/6BbceEVcjow8mhQPYK3eBK"
          },
          "href" : "https://api.spotify.com/v1/artists/6BbceEVcjow8mhQPYK3eBK",
          "id" : "6BbceEVcjow8mhQPYK3eBK",
          "name" : "Skread",
          "type" : "artist",
          "uri" : "spotify:artist:6BbceEVcjow8mhQPYK3eBK"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 104026,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/7DE29RExqYnmgvELjh4vWG"
        },
        "href" : "https://api.spotify.com/v1/tracks/7DE29RExqYnmgvELjh4vWG",
        "id" : "7DE29RExqYnmgvELjh4vWG",
        "name" : "Nouvelle journée",
        "preview_url" : "https://p.scdn.co/mp3-preview/8eff84d1a4e17e707e5cd3f0b1acc24c42c87ed5?cid=null",
        "track_number" : 1,
        "type" : "track",
        "uri" : "spotify:track:7DE29RExqYnmgvELjh4vWG"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/5sXx89ZqVFOUKTl5g2uiST"
          },
          "href" : "https://api.spotify.com/v1/artists/5sXx89ZqVFOUKTl5g2uiST",
          "id" : "5sXx89ZqVFOUKTl5g2uiST",
          "name" : "Casseurs Flowters",
          "type" : "artist",
          "uri" : "spotify:artist:5sXx89ZqVFOUKTl5g2uiST"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 220186,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/5C3TCUckVV2p90RV1jF8Ok"
        },
        "href" : "https://api.spotify.com/v1/tracks/5C3TCUckVV2p90RV1jF8Ok",
        "id" : "5C3TCUckVV2p90RV1jF8Ok",
        "name" : "À l’heure où je me couche",
        "preview_url" : "https://p.scdn.co/mp3-preview/9b17c4d2e9add006303b112fb48eac4c46d15a42?cid=null",
        "track_number" : 2,
        "type" : "track",
        "uri" : "spotify:track:5C3TCUckVV2p90RV1jF8Ok"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/4FpJcNgOvIpSBeJgRg3OfN"
          },
          "href" : "https://api.spotify.com/v1/artists/4FpJcNgOvIpSBeJgRg3OfN",
          "id" : "4FpJcNgOvIpSBeJgRg3OfN",
          "name" : "Orelsan",
          "type" : "artist",
          "uri" : "spotify:artist:4FpJcNgOvIpSBeJgRg3OfN"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 209720,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/7l0tZknEIevghdTejbtrNr"
        },
        "href" : "https://api.spotify.com/v1/tracks/7l0tZknEIevghdTejbtrNr",
        "id" : "7l0tZknEIevghdTejbtrNr",
        "name" : "Quand ton père t’engueule",
        "preview_url" : "https://p.scdn.co/mp3-preview/6e0bd6ea44f486a4efa6909e9c9085a0d09db253?cid=null",
        "track_number" : 3,
        "type" : "track",
        "uri" : "spotify:track:7l0tZknEIevghdTejbtrNr"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/5sXx89ZqVFOUKTl5g2uiST"
          },
          "href" : "https://api.spotify.com/v1/artists/5sXx89ZqVFOUKTl5g2uiST",
          "id" : "5sXx89ZqVFOUKTl5g2uiST",
          "name" : "Casseurs Flowters",
          "type" : "artist",
          "uri" : "spotify:artist:5sXx89ZqVFOUKTl5g2uiST"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 236173,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/76CcoEXwUa68VvshXmJ1JG"
        },
        "href" : "https://api.spotify.com/v1/tracks/76CcoEXwUa68VvshXmJ1JG",
        "id" : "76CcoEXwUa68VvshXmJ1JG",
        "name" : "En boucle",
        "preview_url" : "https://p.scdn.co/mp3-preview/3b3f2f09933db538de7ac5555d89f6ac7cdb94ac?cid=null",
        "track_number" : 4,
        "type" : "track",
        "uri" : "spotify:track:76CcoEXwUa68VvshXmJ1JG"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/6BbceEVcjow8mhQPYK3eBK"
          },
          "href" : "https://api.spotify.com/v1/artists/6BbceEVcjow8mhQPYK3eBK",
          "id" : "6BbceEVcjow8mhQPYK3eBK",
          "name" : "Skread",
          "type" : "artist",
          "uri" : "spotify:artist:6BbceEVcjow8mhQPYK3eBK"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 102613,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/4BgyOVbTb2mHpNWrtGSs6n"
        },
        "href" : "https://api.spotify.com/v1/tracks/4BgyOVbTb2mHpNWrtGSs6n",
        "id" : "4BgyOVbTb2mHpNWrtGSs6n",
        "name" : "Faut qu’on rentre bosser",
        "preview_url" : "https://p.scdn.co/mp3-preview/a71dc6fc18fd7f76267119a7765d530c68ffff49?cid=null",
        "track_number" : 5,
        "type" : "track",
        "uri" : "spotify:track:4BgyOVbTb2mHpNWrtGSs6n"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/5sXx89ZqVFOUKTl5g2uiST"
          },
          "href" : "https://api.spotify.com/v1/artists/5sXx89ZqVFOUKTl5g2uiST",
          "id" : "5sXx89ZqVFOUKTl5g2uiST",
          "name" : "Casseurs Flowters",
          "type" : "artist",
          "uri" : "spotify:artist:5sXx89ZqVFOUKTl5g2uiST"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 252360,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/59g2FzWJbiW48fnNOmmHwj"
        },
        "href" : "https://api.spotify.com/v1/tracks/59g2FzWJbiW48fnNOmmHwj",
        "id" : "59g2FzWJbiW48fnNOmmHwj",
        "name" : "Freestyle Radio Phoenix",
        "preview_url" : "https://p.scdn.co/mp3-preview/385bd9e5a1ea0a032f8d34d9eb7f5428a6c1fa8f?cid=null",
        "track_number" : 6,
        "type" : "track",
        "uri" : "spotify:track:59g2FzWJbiW48fnNOmmHwj"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/4oVERQ0AxzzQ4zip1nMKlt"
          },
          "href" : "https://api.spotify.com/v1/artists/4oVERQ0AxzzQ4zip1nMKlt",
          "id" : "4oVERQ0AxzzQ4zip1nMKlt",
          "name" : "Gringe",
          "type" : "artist",
          "uri" : "spotify:artist:4oVERQ0AxzzQ4zip1nMKlt"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 186026,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/09zldvZV2nAWAJfgxiUibd"
        },
        "href" : "https://api.spotify.com/v1/tracks/09zldvZV2nAWAJfgxiUibd",
        "id" : "09zldvZV2nAWAJfgxiUibd",
        "name" : "Le mal est fait",
        "preview_url" : "https://p.scdn.co/mp3-preview/635ab1fba6eedb45ca65b409bb926b7f789d247c?cid=null",
        "track_number" : 7,
        "type" : "track",
        "uri" : "spotify:track:09zldvZV2nAWAJfgxiUibd"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/5sXx89ZqVFOUKTl5g2uiST"
          },
          "href" : "https://api.spotify.com/v1/artists/5sXx89ZqVFOUKTl5g2uiST",
          "id" : "5sXx89ZqVFOUKTl5g2uiST",
          "name" : "Casseurs Flowters",
          "type" : "artist",
          "uri" : "spotify:artist:5sXx89ZqVFOUKTl5g2uiST"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 157986,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/6xRIJBqIbqfnAkZFsFl15o"
        },
        "href" : "https://api.spotify.com/v1/tracks/6xRIJBqIbqfnAkZFsFl15o",
        "id" : "6xRIJBqIbqfnAkZFsFl15o",
        "name" : "C’est toujours 2 connards dans un abribus",
        "preview_url" : "https://p.scdn.co/mp3-preview/ff5f0a7d8c2a082c7f2ca9a7e7b4b326b263e7f1?cid=null",
        "track_number" : 8,
        "type" : "track",
        "uri" : "spotify:track:6xRIJBqIbqfnAkZFsFl15o"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/5sXx89ZqVFOUKTl5g2uiST"
          },
          "href" : "https://api.spotify.com/v1/artists/5sXx89ZqVFOUKTl5g2uiST",
          "id" : "5sXx89ZqVFOUKTl5g2uiST",
          "name" : "Casseurs Flowters",
          "type" : "artist",
          "uri" : "spotify:artist:5sXx89ZqVFOUKTl5g2uiST"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 219106,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/0dSDiN76BtcK41NQObQvga"
        },
        "href" : "https://api.spotify.com/v1/tracks/0dSDiN76BtcK41NQObQvga",
        "id" : "0dSDiN76BtcK41NQObQvga",
        "name" : "Pas n’importe quel toon",
        "preview_url" : "https://p.scdn.co/mp3-preview/c6cfb99135a73148417dd93ab2b84fdcc4f5c2d9?cid=null",
        "track_number" : 9,
        "type" : "track",
        "uri" : "spotify:track:0dSDiN76BtcK41NQObQvga"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/4FpJcNgOvIpSBeJgRg3OfN"
          },
          "href" : "https://api.spotify.com/v1/artists/4FpJcNgOvIpSBeJgRg3OfN",
          "id" : "4FpJcNgOvIpSBeJgRg3OfN",
          "name" : "Orelsan",
          "type" : "artist",
          "uri" : "spotify:artist:4FpJcNgOvIpSBeJgRg3OfN"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 233253,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/1a4g2LxwxzjwpeA3PAz6K8"
        },
        "href" : "https://api.spotify.com/v1/tracks/1a4g2LxwxzjwpeA3PAz6K8",
        "id" : "1a4g2LxwxzjwpeA3PAz6K8",
        "name" : "J’essaye, j’essaye",
        "preview_url" : "https://p.scdn.co/mp3-preview/91623dac90b8f79db6f71dbe5b7e7ffb7b93f43d?cid=null",
        "track_number" : 10,
        "type" : "track",
        "uri" : "spotify:track:1a4g2LxwxzjwpeA3PAz6K8"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/6BbceEVcjow8mhQPYK3eBK"
          },
          "href" : "https://api.spotify.com/v1/artists/6BbceEVcjow8mhQPYK3eBK",
          "id" : "6BbceEVcjow8mhQPYK3eBK",
          "name" : "Skread",
          "type" : "artist",
          "uri" : "spotify:artist:6BbceEVcjow8mhQPYK3eBK"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 90893,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/0pKlYNSUtJCugw3MTOftfr"
        },
        "href" : "https://api.spotify.com/v1/tracks/0pKlYNSUtJCugw3MTOftfr",
        "id" : "0pKlYNSUtJCugw3MTOftfr",
        "name" : "Promenade imprévue",
        "preview_url" : "https://p.scdn.co/mp3-preview/f5152e0aa113f16bb83bcab3b91b62ea3080039c?cid=null",
        "track_number" : 11,
        "type" : "track",
        "uri" : "spotify:track:0pKlYNSUtJCugw3MTOftfr"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2CDZVMcNl3SaXkoC4UMoQe"
          },
          "href" : "https://api.spotify.com/v1/artists/2CDZVMcNl3SaXkoC4UMoQe",
          "id" : "2CDZVMcNl3SaXkoC4UMoQe",
          "name" : "Diamond Deuklo",
          "type" : "artist",
          "uri" : "spotify:artist:2CDZVMcNl3SaXkoC4UMoQe"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 227720,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/4oJ1hftYjelr4YrhnTJy7u"
        },
        "href" : "https://api.spotify.com/v1/tracks/4oJ1hftYjelr4YrhnTJy7u",
        "id" : "4oJ1hftYjelr4YrhnTJy7u",
        "name" : "Xavier",
        "preview_url" : "https://p.scdn.co/mp3-preview/844da61955c7fc44da28bb1001c409c194761712?cid=null",
        "track_number" : 12,
        "type" : "track",
        "uri" : "spotify:track:4oJ1hftYjelr4YrhnTJy7u"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/5sXx89ZqVFOUKTl5g2uiST"
          },
          "href" : "https://api.spotify.com/v1/artists/5sXx89ZqVFOUKTl5g2uiST",
          "id" : "5sXx89ZqVFOUKTl5g2uiST",
          "name" : "Casseurs Flowters",
          "type" : "artist",
          "uri" : "spotify:artist:5sXx89ZqVFOUKTl5g2uiST"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 78146,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/6prfpNZvYnXROnwwdyD6ql"
        },
        "href" : "https://api.spotify.com/v1/tracks/6prfpNZvYnXROnwwdyD6ql",
        "id" : "6prfpNZvYnXROnwwdyD6ql",
        "name" : "Wondercash",
        "preview_url" : "https://p.scdn.co/mp3-preview/6b7ef3d358ab955ae0692cfb6bee518aef78b9aa?cid=null",
        "track_number" : 13,
        "type" : "track",
        "uri" : "spotify:track:6prfpNZvYnXROnwwdyD6ql"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/6BbceEVcjow8mhQPYK3eBK"
          },
          "href" : "https://api.spotify.com/v1/artists/6BbceEVcjow8mhQPYK3eBK",
          "id" : "6BbceEVcjow8mhQPYK3eBK",
          "name" : "Skread",
          "type" : "artist",
          "uri" : "spotify:artist:6BbceEVcjow8mhQPYK3eBK"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 97293,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/4t9yWKvskdidxUSo55qPqm"
        },
        "href" : "https://api.spotify.com/v1/tracks/4t9yWKvskdidxUSo55qPqm",
        "id" : "4t9yWKvskdidxUSo55qPqm",
        "name" : "On est resté à l’hôtel",
        "preview_url" : "https://p.scdn.co/mp3-preview/9d2b969ca9ad9c41483af12bb624af40aadc991a?cid=null",
        "track_number" : 14,
        "type" : "track",
        "uri" : "spotify:track:4t9yWKvskdidxUSo55qPqm"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/5sXx89ZqVFOUKTl5g2uiST"
          },
          "href" : "https://api.spotify.com/v1/artists/5sXx89ZqVFOUKTl5g2uiST",
          "id" : "5sXx89ZqVFOUKTl5g2uiST",
          "name" : "Casseurs Flowters",
          "type" : "artist",
          "uri" : "spotify:artist:5sXx89ZqVFOUKTl5g2uiST"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 236733,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/7GfmLz1shp0aMwkPYHan0y"
        },
        "href" : "https://api.spotify.com/v1/tracks/7GfmLz1shp0aMwkPYHan0y",
        "id" : "7GfmLz1shp0aMwkPYHan0y",
        "name" : "Si facile",
        "preview_url" : "https://p.scdn.co/mp3-preview/cd99a1594d2107444d627b8814bddc662b43872a?cid=null",
        "track_number" : 15,
        "type" : "track",
        "uri" : "spotify:track:7GfmLz1shp0aMwkPYHan0y"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/5sXx89ZqVFOUKTl5g2uiST"
          },
          "href" : "https://api.spotify.com/v1/artists/5sXx89ZqVFOUKTl5g2uiST",
          "id" : "5sXx89ZqVFOUKTl5g2uiST",
          "name" : "Casseurs Flowters",
          "type" : "artist",
          "uri" : "spotify:artist:5sXx89ZqVFOUKTl5g2uiST"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 215786,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/5p9kj8hbofB2EIzwYAqjlb"
        },
        "href" : "https://api.spotify.com/v1/tracks/5p9kj8hbofB2EIzwYAqjlb",
        "id" : "5p9kj8hbofB2EIzwYAqjlb",
        "name" : "Inachevés",
        "preview_url" : "https://p.scdn.co/mp3-preview/5456e8071d67a5083bd0a2762cde8b7d3d8aac1a?cid=null",
        "track_number" : 16,
        "type" : "track",
        "uri" : "spotify:track:5p9kj8hbofB2EIzwYAqjlb"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/6BbceEVcjow8mhQPYK3eBK"
          },
          "href" : "https://api.spotify.com/v1/artists/6BbceEVcjow8mhQPYK3eBK",
          "id" : "6BbceEVcjow8mhQPYK3eBK",
          "name" : "Skread",
          "type" : "artist",
          "uri" : "spotify:artist:6BbceEVcjow8mhQPYK3eBK"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 174693,
        "explicit" : false,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/0jDZcGvyrWKRlQHIaaQ3Bv"
        },
        "href" : "https://api.spotify.com/v1/tracks/0jDZcGvyrWKRlQHIaaQ3Bv",
        "id" : "0jDZcGvyrWKRlQHIaaQ3Bv",
        "name" : "Quand on descend vers le centre",
        "preview_url" : "https://p.scdn.co/mp3-preview/ee134d49e98bc6aa2406c57fb29b6faf06abd359?cid=null",
        "track_number" : 17,
        "type" : "track",
        "uri" : "spotify:track:0jDZcGvyrWKRlQHIaaQ3Bv"
      } ],
      "limit" : 50,
      "next" : null,
      "offset" : 0,
      "previous" : null,
      "total" : 17
    },
    "type" : "album",
    "uri" : "spotify:album:1jfBSA8KgMLol7Iahnq8wv"
  }, {
    "album_type" : "album",
    "artists" : [ {
      "external_urls" : {
        "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
      },
      "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
      "id" : "2iLpvtffIrQ4bMYrFPRN4x",
      "name" : "Deuce",
      "type" : "artist",
      "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
    } ],
    "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
    "copyrights" : [ {
      "text" : "2012 Five Seven Music",
      "type" : "C"
    }, {
      "text" : "2012 Five Seven Music",
      "type" : "P"
    } ],
    "external_ids" : {
      "upc" : "0886443360617"
    },
    "external_urls" : {
      "spotify" : "https://open.spotify.com/album/5ZxDOGBChlRrLUM74nSkbz"
    },
    "genres" : [ ],
    "href" : "https://api.spotify.com/v1/albums/5ZxDOGBChlRrLUM74nSkbz",
    "id" : "5ZxDOGBChlRrLUM74nSkbz",
    "images" : [ {
      "height" : 600,
      "url" : "https://i.scdn.co/image/ee07f02e045ff4bbfec188475579bd64e393371d",
      "width" : 600
    }, {
      "height" : 300,
      "url" : "https://i.scdn.co/image/88f627d0f1d3d6dc4fd66b28fb04f65e4d892b75",
      "width" : 300
    }, {
      "height" : 64,
      "url" : "https://i.scdn.co/image/d7200d5525cd4228bb6eb4fcdc9838b9272f6884",
      "width" : 64
    } ],
    "label" : "Five Seven Music",
    "name" : "Nine Lives",
    "popularity" : 53,
    "release_date" : "2012-04-24",
    "release_date_precision" : "day",
    "tracks" : {
      "href" : "https://api.spotify.com/v1/albums/5ZxDOGBChlRrLUM74nSkbz/tracks?offset=0&limit=50",
      "items" : [ {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
          },
          "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
          "id" : "2iLpvtffIrQ4bMYrFPRN4x",
          "name" : "Deuce",
          "type" : "artist",
          "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 288426,
        "explicit" : true,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/2oA1lpVrmVPd0c6nMfxQgl"
        },
        "href" : "https://api.spotify.com/v1/tracks/2oA1lpVrmVPd0c6nMfxQgl",
        "id" : "2oA1lpVrmVPd0c6nMfxQgl",
        "name" : "Let's Get It Crackin'",
        "preview_url" : "https://p.scdn.co/mp3-preview/b225a15742e844220402e5ad8669dd0c95824063?cid=null",
        "track_number" : 1,
        "type" : "track",
        "uri" : "spotify:track:2oA1lpVrmVPd0c6nMfxQgl"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
          },
          "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
          "id" : "2iLpvtffIrQ4bMYrFPRN4x",
          "name" : "Deuce",
          "type" : "artist",
          "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 257653,
        "explicit" : true,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/4MsHFaxU4H9O0u1bmBrALd"
        },
        "href" : "https://api.spotify.com/v1/tracks/4MsHFaxU4H9O0u1bmBrALd",
        "id" : "4MsHFaxU4H9O0u1bmBrALd",
        "name" : "Help Me",
        "preview_url" : "https://p.scdn.co/mp3-preview/8bef36dfd931a6596317ce85c3c48b153abb11e0?cid=null",
        "track_number" : 2,
        "type" : "track",
        "uri" : "spotify:track:4MsHFaxU4H9O0u1bmBrALd"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
          },
          "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
          "id" : "2iLpvtffIrQ4bMYrFPRN4x",
          "name" : "Deuce",
          "type" : "artist",
          "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 245760,
        "explicit" : true,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/3smUHBHcUoOjqWRq9dfBDL"
        },
        "href" : "https://api.spotify.com/v1/tracks/3smUHBHcUoOjqWRq9dfBDL",
        "id" : "3smUHBHcUoOjqWRq9dfBDL",
        "name" : "America",
        "preview_url" : "https://p.scdn.co/mp3-preview/39503527cfdc8d1ea3da5b6e2ca5a788060f3a24?cid=null",
        "track_number" : 3,
        "type" : "track",
        "uri" : "spotify:track:3smUHBHcUoOjqWRq9dfBDL"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
          },
          "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
          "id" : "2iLpvtffIrQ4bMYrFPRN4x",
          "name" : "Deuce",
          "type" : "artist",
          "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 219920,
        "explicit" : true,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/370CVdnNKT1AwL417gByZA"
        },
        "href" : "https://api.spotify.com/v1/tracks/370CVdnNKT1AwL417gByZA",
        "id" : "370CVdnNKT1AwL417gByZA",
        "name" : "I Came to Party",
        "preview_url" : "https://p.scdn.co/mp3-preview/800dd0803da850ee29e7c1387f9d51d200702cb5?cid=null",
        "track_number" : 4,
        "type" : "track",
        "uri" : "spotify:track:370CVdnNKT1AwL417gByZA"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
          },
          "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
          "id" : "2iLpvtffIrQ4bMYrFPRN4x",
          "name" : "Deuce",
          "type" : "artist",
          "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 215440,
        "explicit" : true,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/3kDUuMfXuoZantdNXxWVKg"
        },
        "href" : "https://api.spotify.com/v1/tracks/3kDUuMfXuoZantdNXxWVKg",
        "id" : "3kDUuMfXuoZantdNXxWVKg",
        "name" : "The One",
        "preview_url" : "https://p.scdn.co/mp3-preview/b7af0ee170b2becd471184600f77728d69474642?cid=null",
        "track_number" : 5,
        "type" : "track",
        "uri" : "spotify:track:3kDUuMfXuoZantdNXxWVKg"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
          },
          "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
          "id" : "2iLpvtffIrQ4bMYrFPRN4x",
          "name" : "Deuce",
          "type" : "artist",
          "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 273453,
        "explicit" : true,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/6bf4BBB8M7GOrCck9WTYUC"
        },
        "href" : "https://api.spotify.com/v1/tracks/6bf4BBB8M7GOrCck9WTYUC",
        "id" : "6bf4BBB8M7GOrCck9WTYUC",
        "name" : "Freaky Now",
        "preview_url" : "https://p.scdn.co/mp3-preview/ac6dcf859dea045b9947efdf6191a206b70033d9?cid=null",
        "track_number" : 6,
        "type" : "track",
        "uri" : "spotify:track:6bf4BBB8M7GOrCck9WTYUC"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
          },
          "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
          "id" : "2iLpvtffIrQ4bMYrFPRN4x",
          "name" : "Deuce",
          "type" : "artist",
          "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 325146,
        "explicit" : true,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/2GvgLTubZt4SAHLULKQh0S"
        },
        "href" : "https://api.spotify.com/v1/tracks/2GvgLTubZt4SAHLULKQh0S",
        "id" : "2GvgLTubZt4SAHLULKQh0S",
        "name" : "Nobody Likes Me",
        "preview_url" : "https://p.scdn.co/mp3-preview/1fd0916c9f98398ba0d0ed3192f405eddae56124?cid=null",
        "track_number" : 7,
        "type" : "track",
        "uri" : "spotify:track:2GvgLTubZt4SAHLULKQh0S"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
          },
          "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
          "id" : "2iLpvtffIrQ4bMYrFPRN4x",
          "name" : "Deuce",
          "type" : "artist",
          "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 201000,
        "explicit" : true,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/3j21GP40DQMMtGZUoi4xwp"
        },
        "href" : "https://api.spotify.com/v1/tracks/3j21GP40DQMMtGZUoi4xwp",
        "id" : "3j21GP40DQMMtGZUoi4xwp",
        "name" : "Walk Alone",
        "preview_url" : "https://p.scdn.co/mp3-preview/cae0b178317965b818fb7f51b60756b632b321d5?cid=null",
        "track_number" : 8,
        "type" : "track",
        "uri" : "spotify:track:3j21GP40DQMMtGZUoi4xwp"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
          },
          "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
          "id" : "2iLpvtffIrQ4bMYrFPRN4x",
          "name" : "Deuce",
          "type" : "artist",
          "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 283093,
        "explicit" : true,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/1MtmjNbXERQBWmURZPYAa7"
        },
        "href" : "https://api.spotify.com/v1/tracks/1MtmjNbXERQBWmURZPYAa7",
        "id" : "1MtmjNbXERQBWmURZPYAa7",
        "name" : "Till I Drop",
        "preview_url" : "https://p.scdn.co/mp3-preview/99064a99e408251b2f425fc862605902aeabf6af?cid=null",
        "track_number" : 9,
        "type" : "track",
        "uri" : "spotify:track:1MtmjNbXERQBWmURZPYAa7"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
          },
          "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
          "id" : "2iLpvtffIrQ4bMYrFPRN4x",
          "name" : "Deuce",
          "type" : "artist",
          "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 234586,
        "explicit" : true,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/6kka5TbBImaVYyTTkLqrc6"
        },
        "href" : "https://api.spotify.com/v1/tracks/6kka5TbBImaVYyTTkLqrc6",
        "id" : "6kka5TbBImaVYyTTkLqrc6",
        "name" : "Gravestone",
        "preview_url" : "https://p.scdn.co/mp3-preview/67f9fb9a97a8ddacbea46cf099ac023a3fd62438?cid=null",
        "track_number" : 10,
        "type" : "track",
        "uri" : "spotify:track:6kka5TbBImaVYyTTkLqrc6"
      }, {
        "artists" : [ {
          "external_urls" : {
            "spotify" : "https://open.spotify.com/artist/2iLpvtffIrQ4bMYrFPRN4x"
          },
          "href" : "https://api.spotify.com/v1/artists/2iLpvtffIrQ4bMYrFPRN4x",
          "id" : "2iLpvtffIrQ4bMYrFPRN4x",
          "name" : "Deuce",
          "type" : "artist",
          "uri" : "spotify:artist:2iLpvtffIrQ4bMYrFPRN4x"
        } ],
        "available_markets" : [ "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "ID", "IE", "IS", "IT", "JP", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "SE", "SG", "SK", "SV", "TR", "TW", "US", "UY" ],
        "disc_number" : 1,
        "duration_ms" : 257986,
        "explicit" : true,
        "external_urls" : {
          "spotify" : "https://open.spotify.com/track/5cb8n1hE445dri0Hoz4PuQ"
        },
        "href" : "https://api.spotify.com/v1/tracks/5cb8n1hE445dri0Hoz4PuQ",
        "id" : "5cb8n1hE445dri0Hoz4PuQ",
        "name" : "Now You See My Life",
        "preview_url" : "https://p.scdn.co/mp3-preview/2a36584e58f5c689400e7e86ca0231ca0022dfd8?cid=null",
        "track_number" : 11,
        "type" : "track",
        "uri" : "spotify:track:5cb8n1hE445dri0Hoz4PuQ"
      } ],
      "limit" : 50,
      "next" : null,
      "offset" : 0,
      "previous" : null,
      "total" : 11
    },
    "type" : "album",
    "uri" : "spotify:album:5ZxDOGBChlRrLUM74nSkbz"
  } ]
},

	allAlbums = playlists[selectedPlaylist].tracks,
	topInfosTitle = $('.top-infos__title'),
	topInfosAlbum = $('.top-infos__subtitle'),
	visibleCovers = $('.player__middle-cover'),
	currentTrackTitle = $('.player__bottom-title'),
	currentTrackSlider = $('.bottom-track__slider'),
	currentTrackTimer = $('.bottom-track__time'),
	currentTrackElapsed = $('.bottom-track__elapsed'),
	volumeSlider = $('.level__slider'),
	volumeSliderDown = $('.level__slider-down'),
	fastForward = $('.controls__move').eq(1),
	fastRewind = $('.controls__move').eq(0),
	playPause = $('.controls__play-pause'),
	selectedAlbum = 0,
	trackNumber = 0,
	previous = selectedAlbum - 1,
	next = selectedAlbum + 1,
	libraryLength = playlists[selectedPlaylist].tracks.length,
	coverLeft = visibleCovers.eq(0),
	coverCenter = visibleCovers.eq(1),
	coverRight = visibleCovers.eq(2),
	albumTracksLength =  playlists[selectedPlaylist].tracks.length,
	artist =  playlists[selectedPlaylist].tracks[selectedAlbum].artists.name,
	albumType = allAlbums[selectedAlbum].album_type,
	currentAlbum =  playlists[selectedPlaylist].name,
	currentTrack =  playlists[selectedPlaylist].tracks[selectedAlbum].title,
	currentTrackDuration =30000,
	currentCover = extractVideoId(playlists[selectedPlaylist].tracks[selectedAlbum].url);
	$.fn.selectAlbum = function() {
		console.log(trackNumber, albumTracksLength, currentTrack);

		currentTrackTimer.html('10:00').css('left', '-8px');
		currentTrackElapsed.css('width', '0px');
		currentTrackSlider.val('20000');
		topInfosTitle.html(artist);
		coverCenter.css('background-image', 'url('+currentCover+')');
		currentTrackTitle.html(currentTrack);
		currentTrackSlider.attr('max', currentTrackDuration);
		
		if (previous >= 0) {
          console.log(previous);
			var previousCover =extractVideoId( playlists[selectedPlaylist].tracks[previous].url);
			coverLeft.css({'visibility': 'visible', 'background-image': 'url('+previousCover+')'});
		}
		else {
			coverLeft.css('visibility', 'hidden');
		}

		if (next < libraryLength) {

			var nextCover = extractVideoId(playlists[selectedPlaylist].tracks[next].url) ;
			coverRight.css({'visibility': 'visible', 'background-image': 'url('+nextCover+')'});
		}
		else {
			coverRight.css('visibility', 'hidden');
		}
	};

$(document).selectAlbum();
currentTrackSlider.mousedown(function(){
	$(this).mousemove(function(e) {
		var elapsed = e.target.value,
			minutes = Math.floor((elapsed/1000)/60).toFixed(0),
			seconds = Math.floor((elapsed/1000)-(minutes*60)).toFixed(0),
			trackerPosition = Math.floor((elapsed / $(this).attr('max')) * ($(this).width()-16)).toFixed(0);
		if (seconds < 10) {
			seconds = "0"+seconds;
		}
		currentTrackTimer.html(minutes + ':' + seconds).css('left', trackerPosition - 8 + 'px');
		currentTrackElapsed.css('width', trackerPosition + 'px');
	});
});
volumeSlider.mousedown(function(){
	$(this).mousemove(function(e) {
		var volume = e.target.value,
			trackerPosition = Math.floor((volume / $(this).attr('max')) * ($(this).width()-16)).toFixed(0),
			volumeIcon = $('.bottom-inputs__level > .material-icons');
		volumeSliderDown.css('width', trackerPosition + 'px');
		if (volume >= 50) {
			volumeIcon.html('volume_up');
		}
		else if (volume < 50 && volume > 0) {
			volumeIcon.html('volume_down');
		}
		else {
			volumeIcon.html('volume_mute');
		}
	});
});
visibleCovers.eq(0).click(function(){
	selectedAlbum--,
	trackNumber = 0,
	previous = selectedAlbum - 1,
	next = selectedAlbum + 1,
	albumTracksLength = playlists[selectedPlaylist].tracks.length,
	artist =  playlists[selectedPlaylist].tracks[selectedAlbum].author,
	albumType = allAlbums[selectedAlbum].album_type,
	currentAlbum = playlists[selectedPlaylist].name,
	currentTrack = playlists[selectedPlaylist].tracks[selectedAlbum].title,
	//currentAlbumDate = allAlbums[selectedAlbum].release_date.substring(0,4),
	currentTrackDuration = 30000,
	currentCover = extractVideoId(playlists[selectedPlaylist].tracks[selectedAlbum].url);
	
	$(document).selectAlbum();
});
visibleCovers.eq(2).click(function(){
	selectedAlbum++,
	trackNumber = 0,
	previous = selectedAlbum - 1,
	next = selectedAlbum + 1,
	albumTracksLength = playlists[selectedPlaylist].tracks.length,
	artist =  playlists[selectedPlaylist].tracks[selectedAlbum].author,
	albumType = allAlbums[selectedAlbum].album_type,
	currentAlbum = playlists[selectedPlaylist].name,
	currentTrack = playlists[selectedPlaylist].tracks[selectedAlbum].title,
	//currentAlbumDate = allAlbums[selectedAlbum].release_date.substring(0,4),
	currentTrackDuration = 30000,
	currentCover = extractVideoId(playlists[selectedPlaylist].tracks[selectedAlbum].url);
	$(document).selectAlbum();
});
fastForward.click(function(){
	if (trackNumber < albumTracksLength -1) {
		console.log(trackNumber < albumTracksLength);
		trackNumber++,
		previous = selectedAlbum - 1,
		next = selectedAlbum + 1,
		albumTracksLength = playlists[selectedPlaylist].tracks.length,
		artist = allAlbums[selectedAlbum].artists[0].name,
		albumType = allAlbums[selectedAlbum].album_type,
		currentAlbum = allAlbums[selectedAlbum].name,
		currentTrack = allAlbums[selectedAlbum].tracks.items[trackNumber].name,
		//currentAlbumDate = allAlbums[selectedAlbum].release_date.substring(0,4),
		currentTrackDuration = 30000,
		currentCover = extractVideoId(playlists[selectedPlaylist].tracks[selectedAlbum].url);
		
		$(document).selectAlbum();
	}
});
fastRewind.click(function(){
	if(trackNumber > 0) {
		trackNumber--,
		previous = selectedAlbum - 1,
		next = selectedAlbum + 1,
		albumTracksLength = playlists[selectedPlaylist].tracks.length,
		artist = playlists[selectedPlaylist].tracks[selectedAlbum].author,
		albumType = allAlbums[selectedAlbum].album_type,
		currentAlbum = allAlbums[selectedAlbum].name,
		currentTrack =playlists[selectedPlaylist].tracks[selectedAlbum].title,
		//currentAlbumDate = allAlbums[selectedAlbum].release_date.substring(0,4),
		currentTrackDuration = 30000,
		currentCover = extractVideoId(playlists[selectedPlaylist].tracks[selectedAlbum].url);
		$(document).selectAlbum();
	}
});