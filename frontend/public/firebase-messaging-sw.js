// Give the service worker access to Firebase Messaging.
// Note that you can only use Firebase Messaging here. Other Firebase libraries



// are not available in the service worker.
importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-messaging.js');
// Initialize the Firebase app in the service worker by passing in
// your app's Firebase config object.
// https://firebase.google.com/docs/web/setup#config-object
firebase.initializeApp({
  apiKey: "AIzaSyA93yQXg5CPPHWPwLRPwAcE7IgtUMb4Uww",
  authDomain: "msg-project-e8fba.firebaseapp.com",
  projectId: "msg-project-e8fba",
  storageBucket: "msg-project-e8fba.appspot.com",
  messagingSenderId: "930302126573",
  appId: "1:930302126573:web:4ae373f9480381c3429fe4",
  measurementId: "G-6KZ18EXPFY"
});

const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  const notificationTitle = payload.data.title;
  const notificationOptions = {
    body: payload.data.body,
    icon: './MSG_logo_blue_image_dark.png'
  
  };

  console.log(notificationTitle, notificationOptions)
  self.registration.showNotification(notificationTitle, notificationOptions);
});


self.addEventListener('notificationclick', function(event) {
  console.log('[firebase-messaging-sw.js] Notification click Received.', event.notification.data);

  // 알림창 닫기
  event.notification.close();
  // 알림 클릭 시 페이지 열기
  const clickResponsePromise = clients.openWindow('https://i10d109.p.ssafy.io/');
  event.waitUntil(clickResponsePromise);
});