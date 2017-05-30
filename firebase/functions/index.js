const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);


exports.mode = functions.https.onRequest((request, response) => {
	const newMode = request.body.mode;

	admin.database().ref('player').update({mode: newMode}).then(snapshot => {
	    response.status(200).send(newMode);
  	});
});


exports.text = functions.https.onRequest((request, response) => {
	const newText = request.body.text;

	admin.database().ref('player').update({text: newText}).then(snapshot => {
	    response.status(200).send(newText);
  	});
});
