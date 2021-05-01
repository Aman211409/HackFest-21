package com.hackfest21.covigenix.UI.Prediction

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hackfest21.covigenix.R
import kotlinx.android.synthetic.main.fragment_audio_prediction.*
import org.tensorflow.lite.Interpreter
import java.io.IOException
import java.nio.MappedByteBuffer
import java.util.jar.Manifest

class AudioPredictionFragment() : Fragment() {

    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private var recordingStopped: Boolean = false
    private lateinit var chronometer:Chronometer
    lateinit  var tflite:Interpreter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_prediction, container, false)

        var tflite :MappedByteBuffer = FileUtil.loadMappedFile(context,getModelPath())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaRecorder = MediaRecorder()
        output = Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3"

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setOutputFile(output)

        button_start_recording.setOnClickListener {
            if (context?.let { it1 ->
                    ContextCompat.checkSelfPermission(
                        it1,
                        android.Manifest.permission.RECORD_AUDIO)
                } != PackageManager.PERMISSION_GRANTED && context?.let { it1 ->
                    ActivityCompat.checkSelfPermission(
                        it1,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                } != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                     requestPermissions( permissions,0)
            } else {
                startRecording()


            }
        }

        button_stop_recording.setOnClickListener{
            stopRecording()

        }

        button_pause_recording.setOnClickListener {
            pauseRecording()

        }
    }


private fun startRecording() {
    try {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        mediaRecorder?.prepare()
        mediaRecorder?.start()
        state = true
    } catch (e: IllegalStateException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

@SuppressLint("RestrictedApi", "SetTextI18n")
@TargetApi(Build.VERSION_CODES.N)
private fun pauseRecording() {
    if(!recordingStopped){
        mediaRecorder?.pause()
        recordingStopped = true
        button_pause_recording.text = "Resume"
    }else{
        resumeRecording()
    if(state) {
        }
    }
}

@SuppressLint("RestrictedApi", "SetTextI18n")
@TargetApi(Build.VERSION_CODES.N)
private fun resumeRecording() {

    mediaRecorder?.resume()
    chronometer.stop()
    button_pause_recording.text = "Pause"
    recordingStopped = false
}

private fun stopRecording(){
    if(state){
        chronometer.stop()
        mediaRecorder?.stop()
        mediaRecorder?.release()
        state = false
    }
}
}
