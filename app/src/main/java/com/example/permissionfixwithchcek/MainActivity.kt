package com.example.permissionfixwithchcek

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.permissionfixwithchcek.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var storagePermissionCheck: Int = 0
    private var cameraPermissionCheck: Int = 0
    private var locationPermissionCheck: Int = 0
    private var bluetoothPermissionCheck: Int = 0
    private var microphonePermissionCheck: Int = 0
    private var physicalActivityPermissionCheck: Int = 0
    private var setting: String = "Settings"
    private var allow: String = "Allow"


    companion object {
        private const val STORAGE_PERMISSION_CODE = 101
        private const val CAMERA_PERMISSION_CODE = 102
        private const val Location_PERMISSION_CODE = 103
        private const val Bluetooth_PERMISSION_CODE = 104
        private const val Microphon_PERMISSION_CODE = 105

        //        private const val Nearby_Devices_PERMISSION_CODE = 106
        private const val Physical_PERMISSION_CODE = 107

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        supportActionBar?.hide()
        binding.storagePermissionBtn.setOnClickListener {
            checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                STORAGE_PERMISSION_CODE
            )
        }
        binding.cameraPermission.setOnClickListener {
            checkPermission(
                Manifest.permission.CAMERA,
                CAMERA_PERMISSION_CODE
            )
        }
        binding.locationPermission.setOnClickListener {
            checkPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Location_PERMISSION_CODE
            )
        }
        binding.bluetoothPermission.setOnClickListener {
            checkPermission(
                Manifest.permission.BLUETOOTH_SCAN,
                Bluetooth_PERMISSION_CODE
            )
        }
        binding.microphonePermission.setOnClickListener {
            checkPermission(
                Manifest.permission.RECORD_AUDIO,
                Microphon_PERMISSION_CODE
            )
        }
        binding.physicalActivityPermission.setOnClickListener {
            checkPermission(
                Manifest.permission.ACTIVITY_RECOGNITION,
                Physical_PERMISSION_CODE
            )
        }
        binding.closeBtn.setOnClickListener { finish() }
        binding.cameraBtn.setOnClickListener {
            val i = Intent(
                this,
                CameraActivity::class.java
            )
            startActivity(i)
        }


    }


    override fun onStart() {
        super.onStart()
        reCheckPermission()
    }

    override fun onRestart() {
        super.onRestart()
        reCheckPermission()

    }

    private fun reCheckPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.storagePermissionBtn.text = allow
            binding.storagePermissionBtn.visibility = View.GONE
            binding.storagePermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))
        } else {
            binding.storagePermissionBtn.text = allow
        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.cameraPermission.text = allow
            binding.cameraPermission.visibility = View.GONE
            binding.cameraPermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))
        } else {
            binding.cameraPermission.text = allow

        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.locationPermission.text = allow
            binding.locationPermission.visibility = View.GONE
            binding.locationPermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))
        } else {


            binding.locationPermission.text = allow

        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.bluetoothPermission.text = allow
            binding.bluetoothPermission.visibility = View.GONE
            binding.bluetoothPermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))
        } else {
            binding.bluetoothPermission.text = allow

        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.microphonePermission.text = allow
            binding.microphonePermission.visibility = View.GONE
            binding.microphonePermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))
        } else {
            binding.microphonePermission.text = allow

        }
//
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.physicalActivityPermission.text = allow
            binding.physicalActivityPermission.visibility = View.GONE
            binding.physicalPermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))
        } else {
            binding.physicalActivityPermission.text = allow

        }
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT)
                .show()


        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Camera Permission Granted", Toast.LENGTH_SHORT)
                    .show()
                binding.cameraPermission.text = allow
                binding.cameraPermission.visibility = View.GONE
                binding.cameraPermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))
            } else {
                Toast.makeText(this@MainActivity, "Camera Permission Denied", Toast.LENGTH_SHORT)
                    .show()

                binding.cameraPermission.text = setting
                cameraPermissionCheck++
                if (cameraPermissionCheck >= 2) {
                    showAlertDialog(
                        "",
                        "Allow all required Permissions from Settings",
                        "Go to Settings",
                        { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", packageName, null)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        },
                        "Deny Permissions",
                        { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        },
                        false
                    )
                }
            }

        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Storage Permission Granted", Toast.LENGTH_SHORT)
                    .show()
                binding.storagePermissionBtn.text = allow
                binding.storagePermissionBtn.visibility = View.GONE
                binding.storagePermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))
            } else {
                Toast.makeText(this@MainActivity, "Storage Permission Denied", Toast.LENGTH_SHORT)
                    .show()
                binding.storagePermissionBtn.text = setting
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(
                    "extra_object",
                    grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED
                )
                storagePermissionCheck++
                if (storagePermissionCheck >= 2) {
                    showAlertDialog(
                        "",
                        "Allow all required Permissions from Settings",
                        "Go to Settings",
                        { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", packageName, null)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        },
                        "Deny Permissions",
                        { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        },
                        false
                    )
                }


            }
        } else if (requestCode == Location_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Location Permission Granted", Toast.LENGTH_SHORT)
                    .show()
                binding.locationPermission.text = allow
                binding.locationPermission.visibility = View.GONE
                binding.locationPermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))
            } else {
                Toast.makeText(this@MainActivity, "Location Permission Denied", Toast.LENGTH_SHORT)
                    .show()
                binding.locationPermission.text = setting
                locationPermissionCheck++
                if (locationPermissionCheck >= 2) {
                    showAlertDialog(
                        "",
                        "Allow all required Permissions from Settings",
                        "Go to Settings",
                        { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", packageName, null)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        },
                        "Deny Permissions",
                        { dialog, _ ->
                            dialog.dismiss()
                            finish()

                        },
                        false
                    )
                }

            }
        } else if (requestCode == Bluetooth_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this@MainActivity,
                    "Bluetooth Permission Granted",
                    Toast.LENGTH_SHORT
                ).show()
                binding.bluetoothPermission.text = allow
                binding.bluetoothPermission.visibility = View.GONE
                binding.bluetoothPermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))
            } else {
                Toast.makeText(this@MainActivity, "Bluetooth Permission Denied", Toast.LENGTH_SHORT)
                    .show()
                binding.bluetoothPermission.text = setting
                bluetoothPermissionCheck++
                if (bluetoothPermissionCheck >= 2) {
                    showAlertDialog(
                        "",
                        "Allow all required Permissions from Settings",
                        "Go to Settings",
                        { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", packageName, null)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        },
                        "Deny Permissions",
                        { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        },
                        false
                    )
                }
            }
        } else if (requestCode == Microphon_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this@MainActivity,
                    "Microphone Permission Granted",
                    Toast.LENGTH_SHORT
                ).show()
                binding.microphonePermission.text = allow
                binding.microphonePermission.visibility = View.GONE
                binding.microphonePermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Microphone Permission Denied",
                    Toast.LENGTH_SHORT
                )
                    .show()
                binding.microphonePermission.text = setting
                microphonePermissionCheck++
                if (microphonePermissionCheck >= 2) {
                    showAlertDialog(
                        "",
                        "Allow all required Permissions from Settings",
                        "Go to Settings",
                        { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", packageName, null)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        },
                        "Deny Permissions",
                        { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        },
                        false
                    )
                }
            }
        } else if (requestCode == Physical_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Physical Permission Granted", Toast.LENGTH_SHORT)
                    .show()
                binding.physicalActivityPermission.text = allow
                binding.physicalActivityPermission.visibility = View.GONE
                binding.physicalPermissionImg.setImageDrawable(getDrawable(R.drawable.ic_done))

            } else {
                Toast.makeText(this@MainActivity, "Physical Permission Denied", Toast.LENGTH_SHORT)
                    .show()
                binding.physicalActivityPermission.text = setting
                physicalActivityPermissionCheck++
                if (physicalActivityPermissionCheck >= 2) {
                    showAlertDialog(
                        "", "Allow all required Permissions from Settings", "Go to Settings",

                        { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", packageName, null)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        },
                        "Deny Permissions",
                        { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        },
                        false
                    )
                }

            }
        }


    }

    private fun showAlertDialog(
        title: String,
        msg: String,
        positiveLabel: String,
        positiveOnClick: DialogInterface.OnClickListener,
        negativeLabel: String,
        negativeOnClick: DialogInterface.OnClickListener,
        isCancelable: Boolean
    ): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton(positiveLabel, positiveOnClick)
        builder.setNegativeButton(negativeLabel, negativeOnClick)
        builder.setCancelable(isCancelable)

        val alert = builder.create()
        alert.show()
        return alert
    }

}