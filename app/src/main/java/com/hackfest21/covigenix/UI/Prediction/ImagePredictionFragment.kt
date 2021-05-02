package com.hackfest21.covigenix.UI.Prediction

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ml.Model
import kotlinx.android.synthetic.main.fragment_image_prediction.view.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


class ImagePredictionFragment : Fragment() {

    private val TAG = "ImagePredictionFragment"

    var BSelectImage: Button? = null
    var IVPreviewImage: ImageView? = null
    var SELECT_PICTURE = 200

    //   val model2 = model
    //  lateinit var result :TextView
    lateinit var bitmap: Bitmap

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

        BSelectImage?.setOnClickListener { imageChooser() }
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
                if(selectedImageUri!=null){
                    IVPreviewImage?.setImageURI(selectedImageUri)
                }
                bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedImageUri)

                proceed()
            }
        }
    }

    private fun proceed() {
        Log.d("TAG", "proceed: ")

        val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

        val model = Model.newInstance(requireContext())

        val imgData: ByteBuffer = ByteBuffer.allocateDirect(java.lang.Float.BYTES * 224 * 224 * 3)
        imgData.order(ByteOrder.nativeOrder())
        //val bitmap = Bitmap.createScaledBitmap(bp, 60, 60, true)
        val intValues = IntArray(224 * 224)
        resized.getPixels(intValues, 0, resized.width, 0, 0, resized.width, resized.height)

        var pixel = 0

        for (i in 0..223) {
            for (j in 0..223) {
                val `val` = intValues[pixel++]
                imgData.putFloat((`val` shr 16 and 0xFF) / 255f)
                imgData.putFloat((`val` shr 8 and 0xFF) / 255f)
                imgData.putFloat((`val` and 0xFF) / 255f)
            }
        }

        // Creates inputs for reference.
        //val image = TensorImage.fromBitmap(resized)
        val image = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        image.loadBuffer(imgData)

        // Runs model inference and gets result.
        val outputs = model.process(image)
        val probability = outputs.probabilityAsTensorBuffer.floatArray

        val chance = (1-probability[0])*100

        val builder = AlertDialog.Builder(activity)
                .setTitle("Prediction")
                .setMessage("There is $chance% chance of you having COVID.")
                .setPositiveButton("OK", {dial, which -> })

        builder.create().show()
    }
}