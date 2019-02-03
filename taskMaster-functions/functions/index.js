const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.helloWorld = functions.https.onRequest((request, response) => {
 response.send("Hello from Firebase!");
});

// This function sorts their tasks 
exports.stringToReverse = functions.https.onRequest((request, response) => {
    response.send(request.query.split("").reverse().join());
})


// Sort projectTask A-Z
function sortAZ(userId) {

    const userRef = db.collection("tasks").doc();
    userRef.where("assigned", "==", userId);
}

// Sort projectTask by Status

// sort projectTask by User
