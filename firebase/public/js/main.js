// Initialize Firebase
var config = {
    apiKey: "AIzaSyBXhsBZfO6ZjSc9ugiengQlOQjGaTm3-LI",
    authDomain: "fire-home-things.firebaseapp.com",
    databaseURL: "https://fire-home-things.firebaseio.com",
    projectId: "fire-home-things",
    storageBucket: "fire-home-things.appspot.com",
    messagingSenderId: "491717461231"
};
firebase.initializeApp(config);

// Get a reference to the database service
var database = firebase.database();

var likeButton = document.getElementById("like-it");
var likesCounter = document.getElementById("likes-counter");
var mode = document.getElementById("mode");
var text = document.getElementById("text");
var initialized = false;

var likesCountRef = database.ref('likes');
likesCountRef.on('value', function(snapshot) {
    if (!initialized) {
        initialized = true;
        likeButton.removeAttribute("disabled");
    }
    likesCounter.innerText = snapshot.val().value;
});

var playerModeRef = database.ref('player/mode');
playerModeRef.on('value', function(snapshot) {
    mode.innerText = snapshot.val();
});

var playerTextRef = database.ref('player/text');
playerTextRef.on('value', function(snapshot) {
    text.innerText = snapshot.val();
});

likeButton.addEventListener("click", function() {
    likeButton.setAttribute("disabled", "disabled");

    likesCountRef.transaction(function(likes) {
        var current = likes ? likes.value : 0;
        likes.value = current + 1;
        return likes;
    });
});
