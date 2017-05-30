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

var likesCountRef = database.ref('likes');
likesCountRef.on('value', function(snapshot) {
    console.log(snapshot.val().value)
});

function likeIt() {
    likesCountRef.transaction(function(likes) {
        var current = likes ? likes.value : 0;
        likes.value = current + 1;
        return likes;
    });
}
