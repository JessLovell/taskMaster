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
});

// This function takes in a userId and the type of sort desired
exports.sortTasks = functions.https.onRequest((request, response) => {

    const userId = request.query.userId;
    const sortType = request.query.sort;
    let sortedResponse;
    // Check the desired sort:
    if (sortType === "a-z"){
        sortedResponse = sortAZ(userId);
    } else if (sortType === "status"){
        sortedResponse = sortByStatus(userId);
    } else if (sortType === "user") {
        sortedResponse = sortByUser(userId);
    }
    return sortedResponse;
});


// Sort projectTask A-Z
function sortAZ (userId) {
    const userRef = db.collection("tasks").doc();
    userRef.where("assigned", "==", userId);

    //TODO: Logic to order by a-z
}

// Sort projectTask by Status
function sortByStatus (userId) {
    const userRef = db.collection("tasks").doc();
    userRef.where("assigned", "==", userId);

    //TODO: Logic to order by status
}

// sort projectTask by User
function sortByUser (userId) {
    const userRef = db.collection("tasks").doc();
    userRef.where("assigned", "==", userId);

    //TODO: Logic to order by user
}