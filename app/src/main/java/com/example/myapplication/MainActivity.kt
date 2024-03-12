package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    var lightSensor: Sensor? = null
    var pressureSensor: Sensor? = null
    var tempSensor: Sensor? = null
    var rotateSensor: Sensor? = null
    private lateinit var lightText: TextView
    private lateinit var pressureText: TextView
    private lateinit var tempText: TextView
    private lateinit var rotateImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val list: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        println("Size = ${list.size}")
        println(list.joinToString("\n"))

        lightText = findViewById(R.id.lightText)
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if(lightSensor != null) {
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_GAME)
        }
        pressureText = findViewById(R.id.pressureText)
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if(pressureSensor != null) {
            sensorManager.registerListener(this,pressureSensor,SensorManager.SENSOR_DELAY_GAME)
        }
        tempText = findViewById(R.id.tempText)
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if(tempSensor != null) {
            sensorManager.registerListener(this,tempSensor,SensorManager.SENSOR_DELAY_GAME)
        }
        rotateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if(rotateSensor != null) {
            sensorManager.registerListener(this,rotateSensor,SensorManager.SENSOR_DELAY_GAME)
        }
        rotateImage = findViewById(R.id.imageView)

    }

    override fun onSensorChanged(event: SensorEvent?) {

        if(lightSensor == null) {
            lightText.text = "No lux sensor!"
        } else if(event!!.sensor.type == lightSensor!!.type) {
            lightText.text = "Light: ${event.values[0]}"
        }
        if(pressureSensor == null) {
            pressureText.text = "No pressure sensor!"
        } else if(event!!.sensor.type == pressureSensor!!.type) {
            pressureText.text = "Pressure: ${event.values[0]}"
        }
        if(tempSensor == null) {
            tempText.text = "No temp sensor!"
        } else if(event!!.sensor.type == tempSensor!!.type) {
            tempText.text = "Temp: ${event.values[0]}"
        }
        if(event!!.sensor.type == rotateSensor!!.type) {
            rotateImage.rotation = event.values[2]*10
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}