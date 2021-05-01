package com.hackfest21.covigenix.UI.Prediction

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import com.hackfest21.covigenix.R
import kotlinx.android.synthetic.main.fragment_image_prediction.view.*
import org.tensorflow.lite.Interpreter
import java.io.IOException


class ImagePredictionFragment : Fragment() {

            var BSelectImage: Button? = null
            var IVPreviewImage: ImageView? = null
            var SELECT_PICTURE = 200
           private val yourInputImage: Bitmap
           get() = Bitmap.createBitmap(0, 0, Bitmap.Config.ALPHA_8)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_image_prediction, container, false)
        BSelectImage = v.BSelectImage
        IVPreviewImage = v.IVPreviewImage
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
                            configureHostedModelSource()

                        }
                    }
                }
            }


          val CustomRemoteModel:FirebaseModelDownloader
    private fun configureHostedModelSource() {
        val remoteModel =
            CustomRemoteModel
                .Builder(FirebaseModelSource.Builder("your_model_name").build())
                .build()
    }

    private fun createInterpreter(localModel: FirebaseCustomLocalModel): FirebaseModelInterpreter? {
        // [START mlkit_create_interpreter]
        val options = FirebaseModelInterpreterOptions.Builder(localModel).build()
        val interpreter = FirebaseModelInterpreter.getInstance(options)
        // [END mlkit_create_interpreter]

        return interpreter
    }

    private fun checkModelDownloadStatus(remoteModel: FirebaseCustomRemoteModel, localModel: FirebaseCustomLocalModel) {
        // [START mlkit_check_download_status]
        FirebaseModelManager.getInstance().isModelDownloaded(remoteModel)
            .addOnSuccessListener { isDownloaded ->
                val options =
                    if (isDownloaded) {
                        FirebaseModelInterpreterOptions.Builder(remoteModel).build()
                    } else {
                        FirebaseModelInterpreterOptions.Builder(localModel).build()
                    }
                val interpreter = FirebaseModelInterpreter.getInstance(options)
            }
        // [END mlkit_check_download_status]
    }

    private fun addDownloadListener(
        remoteModel: FirebaseCustomRemoteModel,
        conditions: FirebaseModelDownloadConditions
    ) {
        // [START mlkit_remote_model_download_listener]
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
            .addOnCompleteListener {
                // Download complete. Depending on your app, you could enable the ML
                // feature, or switch from the local model to the remote model, etc.
            }
        // [END mlkit_remote_model_download_listener]
    }

    @Throws(FirebaseMLException::class)
    private fun createInputOutputOptions(): FirebaseModelInputOutputOptions {
        // [START mlkit_create_io_options]
        val inputOutputOptions = FirebaseModelInputOutputOptions.Builder()
            .setInputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 224, 224, 3))
            .setOutputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 5))
            .build()
        // [END mlkit_create_io_options]
        return inputOutputOptions
    }

    private fun bitmapToInputArray(): Array<Array<Array<FloatArray>>> {
        // [START mlkit_bitmap_input]
        val bitmap = Bitmap.createScaledBitmap(yourInputImage, 224, 224, true)

        val batchNum = 0
        val input = Array(1) { Array(224) { Array(224) { FloatArray(3) } } }
        for (x in 0..223) {
            for (y in 0..223) {
                val pixel = bitmap.getPixel(x, y)
                // Normalize channel values to [-1.0, 1.0]. This requirement varies by
                // model. For example, some models might require values to be normalized
                // to the range [0.0, 1.0] instead.
                input[batchNum][x][y][0] = (Color.red(pixel) - 127) / 255.0f
                input[batchNum][x][y][1] = (Color.green(pixel) - 127) / 255.0f
                input[batchNum][x][y][2] = (Color.blue(pixel) - 127) / 255.0f
            }
        }
        // [END mlkit_bitmap_input]
        return input
    }

    @Throws(FirebaseMLException::class)
    private fun runInference() {
        val localModel = FirebaseCustomLocalModel.Builder().build()
        val firebaseInterpreter = createInterpreter(localModel)!!
        val input = bitmapToInputArray()
        val inputOutputOptions = createInputOutputOptions()

        // [START mlkit_run_inference]
        val inputs = FirebaseModelInputs.Builder()
            .add(input) // add() as many input arrays as your model requires
            .build()
        firebaseInterpreter.run(inputs, inputOutputOptions)
            .addOnSuccessListener { result ->
                // [START_EXCLUDE]
                // [START mlkit_read_result]
                val output = result.getOutput<Array<FloatArray>>(0)
                val probabilities = output[0]
                // [END mlkit_read_result]
                // [END_EXCLUDE]
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }
        // [END mlkit_run_inference]
    }

    @Throws(IOException::class)
    private fun useInferenceResult(probabilities: FloatArray) {
        // [START mlkit_use_inference_result]
        val reader = BufferedReader(
            InputStreamReader(assets.open("retrained_labels.txt")))
        for (i in probabilities.indices) {
            val label = reader.readLine()
            Log.i("MLKit", String.format("%s: %1.4f", label, probabilities[i]))
        }
        // [END mlkit_use_inference_result]
    }
}


        }


