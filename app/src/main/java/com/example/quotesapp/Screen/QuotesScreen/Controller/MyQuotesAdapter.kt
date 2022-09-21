package com.example.quotesapp.Screen.QuotesScreen.Controller

import android.content.*
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quotesapp.R
import com.example.quotesapp.Screen.QuotesScreen.ModelClass.ModelDataQuotes
import com.example.quotesapp.Screen.QuotesScreen.Screen.QuotesActivity
import com.example.quotesapp.Utils.DBHelper
import org.w3c.dom.Text
import java.io.File
import java.io.IOException
import java.lang.reflect.Array.get
import java.util.*

class MyQuotesAdapter(
    val quotesActivity: QuotesActivity,
    val list: ArrayList<ModelDataQuotes>,
    val images: Array<Int>
) :
    RecyclerView.Adapter<MyQuotesAdapter.ViewData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(quotesActivity).inflate(R.layout.quotes_item, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.imageView.setImageResource(images.get(2))
        holder.quotesTxt.text = list[position].quote

        holder.likeBtn.setOnClickListener {
//            DBHelper(quotesActivity).updateData(list[position]._id, "1")
            holder.likeImg.setImageResource(R.drawable.red_like)

            Toast.makeText(quotesActivity, "Added in WishList", Toast.LENGTH_LONG).show()


//            var l1 = DBHelper(quotesActivity).readDataLike()

//            if (l1[position].liked.equals(0)) {
//                holder.likeImg.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//            } else {
//                holder.likeImg.setImageResource(R.drawable.red_like)
//            }

//            quotesActivity.setupRV(l1)
        }

        holder.downloadBtn.setOnClickListener {

            holder.img.buildDrawingCache()
            holder.img.setDrawingCacheEnabled(true)
            val bitamp: Bitmap = holder.img.getDrawingCache()
            try {
                saveBitmap(
                    quotesActivity,
                    bitamp,
                    Bitmap.CompressFormat.PNG,
                    "image/*",
                    "newimg.png"
                )
            } catch (e: Exception) {
            }
            holder.img.setDrawingCacheEnabled(false)

            Toast.makeText(quotesActivity, "Download Image Successfully", Toast.LENGTH_LONG).show()
        }

        holder.copyBtn.setOnClickListener {
            val myClipboard =
                quotesActivity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val myClip: ClipData = ClipData.newPlainText("Label", list[position].quote)
            myClipboard.setPrimaryClip(myClip)
            Toast.makeText(quotesActivity, "Copied Successfully", Toast.LENGTH_LONG).show()
        }

        holder.shareBtn.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "image/plain"
            val shareBody =
                "Hello USER,\nPlease Rate Quotes App On Play Store\n⭐⭐⭐⭐⭐\n\nYOUR QUOTE\n \uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\n\n ${list[position].quote}"
            sharingIntent.putExtra(Intent.EXTRA_TEXT, list[position].quote)
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            quotesActivity.startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        holder.imageView.setOnClickListener {
            val random = Random()
            val randomNumber = random.nextInt(10)
            holder.imageView.setImageResource(images.get(randomNumber))
//            Toast.makeText(quotesActivity,"${image.get(randomNumber)}",Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var quotesTxt = itemView.findViewById<TextView>(R.id.quotesTxt)
        var likeImg = itemView.findViewById<ImageView>(R.id.likeImg)
        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var img = itemView.findViewById<RelativeLayout>(R.id.img)
        var likeBtn = itemView.findViewById<RelativeLayout>(R.id.likeBtn)
        var downloadBtn = itemView.findViewById<RelativeLayout>(R.id.downloadBtn)
        var copyBtn = itemView.findViewById<RelativeLayout>(R.id.copyBtn)
        var shareBtn = itemView.findViewById<RelativeLayout>(R.id.shareBtn)
    }

    @Throws(IOException::class)
    fun saveBitmap(
        context: Context,
        bitmap: Bitmap,
        format: Bitmap.CompressFormat,
        mimeType: String,
        displayName: String
    ): Uri? {
        val relativeLocation = Environment.DIRECTORY_DCIM + File.separator + "PhotoMaker"
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
        val resolver = context.contentResolver
        var uri: Uri? = null
        return try {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            uri = resolver.insert(contentUri, values)
            if (uri == null) throw IOException("Failed to create new MediaStore record.")
            resolver.openOutputStream(uri).use { stream ->
                if (stream == null) throw IOException("Failed to open output stream.")
                if (!bitmap.compress(
                        format,
                        95,
                        stream
                    )
                ) throw IOException("Failed to save bitmap.")
            }
            uri
        } catch (e: IOException) {
            if (uri != null) {
                // Don't leave an orphan entry in the MediaStore
                resolver.delete(uri, null, null)
            }
            throw e
        }
    }


}
