const functions = require("firebase-functions");

// The Firebase Admin SDK to access Firestore.
const admin = require("firebase-admin");
admin.initializeApp();

const userRef = admin.firestore().collection("users");

// Create and Deploy Your First Cloud Functions
// https://firebase.google.com/docs/functions/write-firebase-functions

// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", { structuredData: true });
//   response.send("Hello from Firebase!");
// });

exports.addUserToFirestore = functions.auth.user().onCreate((user) => {
  // console.log(Date());

  return userRef.doc(user.uid).set({
    email: user.email,
    username: user.displayName,
    registered: Date()
  });
});

exports.removeUserToFirestore = functions.auth.user().onDelete((user) => {
  return userRef.doc(user.uid).delete();
});

exports.updateUserUsername = functions.https.onCall(async (data, context) => {
  const userRef = admin.firestore().collection("users");

  if (!context.auth) {
    throw new functions.https.HttpsError(
      "unauthenticated",
      "User trying to update username while unauthenticated"
    );
  }

  console.log(context.auth.uid);
  console.log(data.username);

  await admin
    .auth()
    .updateUser(context.auth.uid, { displayName: `${data.username}` })
    .catch((e) => {
      console.log(`auth error updateusername ${e}`);
      throw new functions.https.HttpsError(
        "unknown",
        `failed to update auth username: ${e} ${context.auth.uid} ${data.username}`
      );
    });

  console.log(`auth update worked`);

  return await userRef.doc(context.auth.uid).update({ username: data.username }).catch((e) => {
    console.log(`firebase erro update username: ${e}`);
    throw new functions.https.HttpsError(
      "unknown",
      `failed to update databae username: ${e} ${context.auth.uid} ${data.username}`
    );
  });
});
