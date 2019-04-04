# Lambda com.example.android_lambdamessages.Message Board

## Introduction

For this project, you will build an app to connect to an existing API built by a Lambda Instructor. This API give you access to a message board used by other lambda programs, most notably, the iOS students.

In order to save on time for you to write it, this app will not utilize the MVVM architecture.

## Instructions

### Part 1 - Build Models
This project uses a firebase API which is unique as it doesn't enforce any structure and is essentially a large JSON file. As such, you must be careful in with your URL structure as well as the structure of JSON objects in your POSTs
1. Create a POJO class called `com.example.android_lambdamessages.Message` with the following data members
  ```java
  String sender, text, id;
  double timestamp;
  ```
2. Create constructors which accept all members, sender and text, and a JSONObject
> * The key's for the JSON object match the names of the variables
> * The default value for timestamp will be `System.currentTimeMillis() / 1000` which gives us time since the Unix Epoch (January 1970) in seconds
> * The default value for id will be null
3. Create another POJO class called `MessageBoard` with the following data members

   ```Java
   String title, identifier;
   ArrayList<com.example.android_lambdamessages.Message> messages;
   ```

4. Create a constructor which accepts a title and identifier

   ```JSON
    "-Lb_2nzrahrdW2G38H5u": {
           "messages": {
               "-LQv-QhtT6JHsI1r_XwP": {
                   "sender": "Chance",
                   "text": "Inaugural Android com.example.android_lambdamessages.Message",
                   "timestamp": 1541809486
               },
               "-LQv-VoXq9fnkka-jLQe": {
                   "sender": "Chance",
                   "text": "First",
                   "timestamp": 1541809507
               }
           },
           "title": "Android Thread"
       }
   ```

5. Look at the sample message board structure. There are a few things that make this challenging
  * The top level key (`-Lb_2nzrahrdW2G38H5u`) is a value that we need to store, it is how we will post messages to this thread.
  * The items in the `messages` object are their own objects and not members of a `JSONArray`
6. Instead of just accepting a `JSONObject` we'll have to also accept a `String` which will be the value for `identifier`
7. When constructing `messages` and adding them to our `List`, we need to use the line `getJSONObject("messages").names();`
  * this will grab the messages object which holds all messages and then give us a `JSONArray` object which has a list of all the keys in the messages object
8. Take the resulting value and step through it in a for loop.
9. With each pass of the loop, get a JSONObject using the name in the list and pass it into your message constructor
10. Build getters for each of your attributes
1. Add the keywords `implements Parcelable` to your `MessageBoard` object	
2. Override the `writeToParcel` method, accepting a `Parcel` object and an `int`	
3. call `parcel.write`*Type* passing in all of your current members.
  * Convert your List to an array by calling `toArray` on it, the use `writeArray` to write your result to the parcel
4. Write a constructor which accepts a `Parcel` and calls `parcel.read`*Type* be sure to read in the same order you wrote the members	
  * To build the messages list, you'll have to call`parcel.readArray(com.example.android_lambdamessages.Message.class.getClassLoader())` to get an array of `Objects`, then instantiate your `ArrayList` of messages. Finally, loop through the object array, cast each element to a message, and then add them to your list
5. Click the error message in the class signature and add all unimplemented methods and leave the defaultvalues
5. Repeat the previous 5 steps in your `com.example.android_lambdamessages.Message` class to make it a `Parcelable` as well.

### Part 2 - Read com.example.android_lambdamessages.Message Boards
Because this API retreives all messages and boards at once, we'll store them all at once.
1. Create a `NetworkAdapter` class like you have before. Write a `httpRequest` method in this class like before as well
  * Check Part 2 of the [NetworkBasics](https://github.com/LambdaSchool/Android_NetworkBasics) for instructions on how to do this
2. Create a `MessageBoardDao` class
3. Create a method called `getMessageBoards` which returns an `ArrayList` of `MessageBoard` objects
4. The base url for the firebase database is `https://lambda-message-board.firebaseio.com/`. Add ".json" to the end of that url to retreive the database
5. Test that URL in postman using a `GET` request to make sure you have the correct URL
6. Call `NetworkAdapter.httpRequest`, pass in that URL, store the String result
7. Now we're on the other side of the `MessageBoard` constructor. We again need to get the names of all the keys in the object and loop through the array to create each message board and add it to the array.
  * Be sure to pass the object name into the constructor as well since you'll need it to write messages
8. Wrap each JSON call in a try/catch block as we've done before
9. Return your newly filled array

### Part 3 - Display Data
1. Create a list activity to display all the boards
2. You'll need to call the `MessageBoardDao.getAllBoards` method in a background thread. You can do this with a basic java thread or AsyncTask
> Remember, you can only manipulate the ui (add items) from the UI thread
3. Add a click listener to each board in the list
4. Build an intent to launch the next activity you'll create, one to show the messages from a single messageboard
5. Add the board to your intent
6. Create the next activity
7. In the oncreate method of the new activity call `getIntent().getParcelableExtra(MESSAGE_BOARD_KEY)` and store it as your message board.
8. Use that board object to display the information and all the messages for your board (if you wanted to use a `RecyclerView` this would be the activity to use it)

### Part 4 - Handle a Post Request

1. In order to post a message to a message board with this API, you must have the right url, complete a post request, and pass the JSON object to be posted.
2. The url to post a message consists of the base url, the board id, the `messages` keyword and then ending the whole thing with `.json`
  * The final URL would be `https://lambda-message-board.firebaseio.com/THREAD_ID_HERE/messages.json`
3. To complete your post request, we must adapt your existing `httpRequest` method to be able to handle `POST` requests as well as `GET` requests. Thankfully, there aren't many changes which need to be done to add this functionality
4. First, you'll need to accept two additional parameters in the method signature. a String for the request type and a JSONObect for the request body.
  * It would be best to add String constant values for "POST" and "GET"
5. Next, you'll need to add a check for the request type. Look for the following block in your method

   ```Java
               connection.setConnectTimeout(TIMEOUT);
               connection.setRequestMethod("GET");
               connection.connect();
   ```

6. You'll need to replace the hardcoded request type with the type passed in the signature

7. Next, we will wrap the `connect` line with an if statement. If the request type is "GET", we'll perform the connect as usual. If not, we'll perform the `POST` request

8. If the type is "POST" the connection procedure is a bit different. First you'll have to get an `OutputStream` object by calling `getOutputStream()` on your `connection` object

9. Once you have your output stream, you'll need to write the body of your post request to it. We do that by calling its `write` method. However, unlike with a `PrintStream`, we must write our data as a byte array. We can get a byte array from the `JSONObject` by first converting it to a string with `toString` and then convert the result to bytes with `getBytes`. Pass those bytes to the `outputStream.write` method

10. Finally, close the `OutputStream` with the `close` method

### Part 5 - Post a com.example.android_lambdamessages.Message
1. Add edit fields for message text and sender to your thread activity
  * For a challenge, you can create a new activity for settings and store the sender information there
2. Add a button to trigger the posting of the message, have it retreive the values from your edit fields and store them. We'll add the rest of the functionality in a few minutes
3. In your `MessageBoardDao` class, create a static class called `newMessage` which accepts a String (the message board id) and a message object
4. In this method, you'll need to use the board id to assemble the URL as shown in Part 4.2
6. Build a JSON string with the values for the text, sender, and current timestamp
  * The JSON string should be something like this (with the %s/%f values replaced)
```json
    {  
       "text":"%s",
       "sender":"%s",
       "timestamp":%f
    }
```
  * To get the timestamp, call `System.currentTimeMillis()` and divide the result by 1000 to convert to seconds
  * Remember a JSON string just a String value in the JSON format

5. Call your `httpRequest` method, pass it "POST" as the request type and your JSON String for the body
6. Use the result from the request method to construct a JSONObject
7. get the value for the "name" attribute from that JSON object and use it to set the id value for the passed in message
  * Remember that you can call setter methods on passed parameters
8. In your post message listener, create a new message object with the sender and text string (you may need to write another constructor)
9. call your new method in a background thread with the id from your message board and the message object you jsuit created
9. When the post is successful, add your new message object to the UI
  * Be sure to only manipulate the UI from the UI thread


#### Challenge
Use recycler views for one or both activities
