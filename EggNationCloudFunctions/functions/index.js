const nodemailer = require("nodemailer");

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

/**
 * 
 */
exports.addUserToFirestore = functions.auth.user().onCreate(async (user) => {
  return await userRef.doc(user.uid).set({
    email: user.email,
    username: user.displayName,
    registered: Date(),
  });
});

/**
 * 
 */
exports.removeUserToFirestore = functions.auth.user().onDelete((user) => {
  return userRef.doc(user.uid).delete();
});

/**
 * 
 */
exports.sendMeEmail = functions.https.onCall(async (data, context) => {
  if (!context.auth) {
    throw new functions.https.HttpsError(
      "unauthenticated",
      "User trying to claim a prize while not authenticated"
    );
  }

  const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
      user: `${process.env.GMAIL_EMAIL}`,
      pass: `${process.env.GMAIL_PASS}`,
    },
  });

  var mailOptions = {
    from: `${process.env.GMAIL_EMAIL}`,
    to: `${process.env.GMAIL_EMAIL}`,
    subject: `WINNER: ${data.prizeId}`,
    text: `email: ${data.email} -- country: ${data.country} -- region: ${data.region} -- address: ${data.address}`,
  };

  transporter.sendMail(mailOptions, (err, info) => {
    if (err) {
      throw new functions.https.HttpsError(
        "unknown",
        "Failed to claim prize, please try again. If error persists, please contact us"
      );
    } else {
      return true;
    }
  });
});

exports.updateUserEmail = functions.https.onCall(async (data, context) => {
  const userRef = admin.firestore().collection("users");

  if (!context.auth) {
    throw new functions.https.HttpsError(
      "unauthenticated",
      "User trying to update email while unauthenticated"
    );
  }

  console.log(context.auth.uid);
  console.log(data.email);

  await admin
    .auth()
    .updateUser(context.auth.uid, {
      email: data.email,
      emailVerified: false,
    })
    .catch((e) => {
      console.log(`auth error updateemail ${e}`);
      throw new functions.https.HttpsError(
        "unknown",
        `failed to update auth email: ${e} ${context.auth.uid} ${data.email}`
      );
    });

  console.log(`auth update worked`);

  return await userRef
    .doc(context.auth.uid)
    .update({ email: data.email })
    .catch((e) => {
      console.log(`firebase erro update email: ${e}`);
      throw new functions.https.HttpsError(
        "unknown",
        `failed to update database email: ${e} ${context.auth.uid} ${data.email}`
      );
    });
});
