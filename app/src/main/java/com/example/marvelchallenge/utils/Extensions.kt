package com.example.marvelchallenge.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marvelchallenge.data.models.ExpandableEvent
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.model.Thumbnail
import java.security.MessageDigest
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun ImageView.loadFromUrl(imageSize: String, thumbnail: Thumbnail) {
    Glide.with(this)
        .load(thumbnail.path + imageSize + thumbnail.extension)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this)
}

fun String.formatDate(): String? {
    val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = inputFormat.parse(this)
    val outPutFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return date?.let {
        outPutFormat.format(it).toString()
    }
}

fun Event.asExpandableEvent() = ExpandableEvent(id, title, start, end, thumbnail, comics, false)

fun String.toMD5(): String {
    val md5 = "MD5"
    val digest: MessageDigest = MessageDigest.getInstance(md5)
    digest.update(this.toByteArray())
    val messageDigest: ByteArray = digest.digest()

    val hexString = StringBuilder()
    for (aMessageDigest in messageDigest) {
        var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
        while (h.length < 2) h = "0$h"
        hexString.append(h)
    }
    return hexString.toString()
}
