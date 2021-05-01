package com.hackfest21.covigenix.UI.Prediction

import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract.Attendees.query
import android.provider.CalendarContract.Instances.query
import android.provider.CalendarContract.Reminders.query
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentResolverCompat.query
import androidx.fragment.app.Fragment
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ml.Model2
import kotlinx.android.synthetic.main.fragment_image_prediction.view.*
import org.tensorflow.lite.TensorFlowLite
import org.tensorflow.lite.support.image.TensorImage
import java.io.IOException


class ImagePredictionFragment : Fragment() {

    var BSelectImage: Button? = null
    var IVPreviewImage: ImageView? = null
    var SELECT_PICTURE = 200

    //   val model2 = model
    //  lateinit var result :TextView
    var bitmap: Bitmap? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_image_prediction, container, false)
        BSelectImage = v.BSelectImage
        IVPreviewImage = v.IVPreviewImage
        // result = v.PredictingText
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageChooser()
    }

    fun imageChooser() {

        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                val selectedImageUri: Uri? = data!!.data
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage?.setImageURI(selectedImageUri)
                    val pickedImage: Uri = data!!.data!!

                    // Let's read picked image path using content resolver
                    // Let's read picked image path using content resolver
                    val filePath = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor: Cursor =
                        requireActivity().contentResolver.query(
                            pickedImage,
                            filePath,
                            null,
                            null,
                            null
                        )!!
                    cursor.moveToFirst()
                    val imagePath: String =
                        cursor.getString(cursor.getColumnIndex(filePath[0]))

                    val options = BitmapFactory.Options()
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888
                    bitmap = BitmapFactory.decodeFile(imagePath, options)
                    //     analyze()
                    cursor.close();

                }
            }
        }
    }
}

//              fun analyze() {
//                  val outputs = model2.process(bitmap)
//                      .probabilityAsCategoryList.apply {
//                          sortByDescending { it.score } // Sort with highest confidence first
//                      }.take(1) // take the top results
//              }


//



