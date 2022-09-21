package com.example.quotesapp.Screen.QuotesScreen.Screen

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotesapp.R
import com.example.quotesapp.Screen.QuotesScreen.Controller.MyQuotesAdapter
import com.example.quotesapp.Screen.QuotesScreen.ModelClass.ModelDataQuotes
import com.example.quotesapp.Utils.DBHelper
import com.example.quotesapp.databinding.ActivityQuotesBinding

class QuotesActivity : AppCompatActivity() {

    companion object {
        lateinit var quotesBinding: ActivityQuotesBinding
    }

    var urlImage = arrayOf(
        "https://soulsofsilver.com/wp-content/uploads/2020/10/iStock-688148142-1.jpg",
        "https://avatars.mds.yandex.net/i?id=c3ee0cf40c9f4a789c753d90e49aa51a-5876743-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=5b68b7a1a5f3fedf0ab4da5443a52d9e-4697805-images-thumbs&n=13",
        "https://adoptlondon.org.uk/app/uploads/2020/10/SomePeople_Teddy-scaled.jpg",
        "https://avatars.mds.yandex.net/i?id=feb23af0022d282bf89d0c68bfd0ad70-5666076-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=5bfb47d694cd058fea9ddb4d1332561e-5452061-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=8a690bf1d49ac6418e5d0bc0bf83afa6-5877309-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=dd300eab1cf9c0aecef7043b1a6a26d9-4055786-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=529a072dba0bcbf86c18d537890cf577-5474261-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=29c257ac28965946a5ecf2fad05e2116-5465325-images-thumbs&n=13"
    )

    var images = arrayOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6,
        R.drawable.image7,
        R.drawable.image8,
        R.drawable.image9,
        R.drawable.image10,
        R.drawable.image11,
        R.drawable.image12
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quotesBinding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(quotesBinding.root)

        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        var id = intent.getStringExtra("n1")
        var name = intent.getStringExtra("n2")

        quotesBinding.backBtn.setOnClickListener {
            onBackPressed()
        }

        quotesBinding.catName.text = name

        var list = DBHelper(this).readDataQuotes(id!!)
        setupRV(list)

    }

    fun setupRV(l1: ArrayList<ModelDataQuotes>) {
        var adapter = MyQuotesAdapter(this, l1, images)
        var layoutManager = LinearLayoutManager(this)
        quotesBinding.rvViewQuotes.layoutManager = layoutManager
        quotesBinding.rvViewQuotes.adapter = adapter
    }
}