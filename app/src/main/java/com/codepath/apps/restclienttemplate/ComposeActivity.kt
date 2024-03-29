package com.codepath.apps.restclienttemplate

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class ComposeActivity : AppCompatActivity() {

    lateinit var etCompose: EditText
    lateinit var btnTweet: Button
    lateinit var client: TwitterClient
    lateinit var tweetCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etCompose = findViewById(R.id.etTweetCompose)
        btnTweet = findViewById(R.id.btnTweet)
        client = TwitterApplication.getRestClient(this)

        tweetCount = findViewById(R.id.count_tweet)
//        tILayout = findViewById(R.id.tILayout)

        etCompose.addTextChangedListener(object : TextWatcher{
            val tweetContent = etCompose.text
//            val tweetCounter = tweetContent.length
//            val maxChar = 280
//            val charLeft = maxChar

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (tweetContent.isNotEmpty() && tweetContent.length <= 280){
//                    btnTweet.isEnabled = true
//                } else { btnTweet.isEnabled = false}
                btnTweet.isEnabled = tweetContent.isNotEmpty() && tweetContent.length <= 280
                tweetCount.text = (280 - etCompose.text.toString().trim().length).toString()
                }


        })
        // Handling the user's click on the tweet button
        btnTweet.setOnClickListener{

            // Grab the content of edittext (etCompose)
            val tweetContent = etCompose.text.toString()

            // 1.Make sure the tweet isn't empty
            if (tweetContent.isEmpty()){
                Toast.makeText(this, "Empty tweets not allowed!", Toast.LENGTH_SHORT).show()
                //Look into displaying SnackBar message
            } else
            // 2. Make sure the tweet is under character count
                if (tweetContent.length > 280){
                Toast.makeText(
                    this,
                    "Tweet is too long! Limit is 280 characters", Toast.LENGTH_SHORT).show()

            } else {
                    Toast.makeText(this, "Tweet Success!", Toast.LENGTH_SHORT).show()
                client.publishTweet(tweetContent, object : JsonHttpResponseHandler(){

                    override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                        Log.i(TAG, "Successfully published tweet!")

                        val tweet = Tweet.fromJson(json.jsonObject)
                        val intent = Intent()
                        intent.putExtra("tweet", tweet)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.e(TAG, "Failed to publish tweet", throwable)
                    }
                })
            }
        }
    }
//    private fun textFocusListener
    companion object{
        val TAG = "ComposeActivity"
    }
}
