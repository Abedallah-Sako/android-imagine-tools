package com.imagine.android_imagine_tools.tools.ui.dialogs.imageSelectionMethod

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.imagine.android_imagine_tools.tools.R
import com.imagine.android_imagine_tools.tools.databinding.DialogImageSelectionMethodBinding
import com.imagine.android_imagine_tools.tools.ui.views.BaseBottomSheetDialogFragment
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


class ImageSelectionMethodDialog : BaseBottomSheetDialogFragment() {

    //binding
    private var _binding: DialogImageSelectionMethodBinding? = null
    private val binding get() = _binding!!

    //view Model
    private val viewModel: ImageSelectionMethodViewModel by viewModels()

    //camera - gallery ResultLaunchers
    private lateinit var galleryLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private lateinit var image: Uri

    private lateinit var file: File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initialize provider
        ImageSelectionMethodConstants.init(context)


        //initializing activityResultLauncher outside on create causes an exception if the fragment is recreated
        setupActivityResultLauncher()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogImageSelectionMethodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupCameraImageUri()
        setupListeners()
        setupObservers()


    }

    private fun setupUI() {
        //background color
        binding.dialogImageSelectionMainContainer.background.colorFilter = PorterDuffColorFilter(
            ImageSelectionMethodConstants.backGroundColor,
            PorterDuff.Mode.SRC_IN
        )

        binding.dialogImageSelectionCameraBtnContentContainer.setBackgroundColor(
                ImageSelectionMethodConstants.cameraButtonBackgroundColor

        )

        binding.dialogImageSelectionGalleryBtnContentContainer.setBackgroundColor(
            ImageSelectionMethodConstants.galleryButtonBackgroundColor
        )

        //icon color
        binding.cameraImageFilterView.imageTintList = ColorStateList.valueOf(ImageSelectionMethodConstants.cameraIconColor)
        binding.galleryImageFilterView2.imageTintList = ColorStateList.valueOf(ImageSelectionMethodConstants.galleryIconColor)


        //button text color
        binding.imageSelectionMethodCameraTextView.setTextColor(ImageSelectionMethodConstants.cameraButtonTextColor)
        binding.imageSelectionMethodGalleryTextView.setTextColor(ImageSelectionMethodConstants.galleryButtonTextColor)

        //button strings
        binding.imageSelectionMethodCameraTextView.text = ImageSelectionMethodConstants.cameraString
        binding.imageSelectionMethodGalleryTextView.text =
            ImageSelectionMethodConstants.galleryString

    }

    private fun setupCameraImageUri() {

        //initialize file
        file = File.createTempFile("Img_", ".jpeg", requireActivity().cacheDir).also {
            it.deleteOnExit()
        }

        //get file uri
        image = FileProvider.getUriForFile(
            requireActivity(),
            ImageSelectionMethodConstants.fileProviderAuthority,
            file
        )

    }

    private fun setupListeners() {

        //camera button listener
        binding.dialogImageSelectionCameraBtn.setOnClickListener {
            launchCamera()
        }

        //gallery button listener
        binding.dialogImageSelectionGalleryBtn.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

    }

    private fun setupObservers() {
        viewModel.setImageName(null)



        viewModel.imageName.observe(viewLifecycleOwner) {
            if (it != null) {
                showLoadingDialog(false)
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        //if image was selected through gallery, get file from uri
                        val processedFile = if (file.length() == 0L) {
                            saveUriToFile(image)
                        } else {
                            file
                        }

                        //check extension
                        if (processedFile == null || (processedFile.extension != "jpeg" && processedFile.extension != "jpg" && processedFile.extension != "png")) {

                            requireActivity().runOnUiThread {
                                val builder: AlertDialog.Builder =
                                    AlertDialog.Builder(requireActivity())
                                builder.setMessage(getString(R.string.extension_not_supported_error))
                                    .setPositiveButton(
                                        android.R.string.ok
                                    ) { _, _ ->
                                        dismissLoadingDialog()
                                        dismiss()
                                    }.create().show()
                            }

                            return@launch
                        }


                        val compressedImage =
                            Compressor.compress(requireActivity(), processedFile)

                        withContext(Dispatchers.Main) {
                            listener?.invoke(Pair(it, compressedImage))
                        }

                        dismissLoadingDialog()
                        dismiss()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else {
                dismissLoadingDialog()
            }
        }
    }

    @Suppress("DEPRECATION")

    private fun saveUriToFile(image: Uri): File? {


        try {
            var imageBitmap: Bitmap? = null
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                imageBitmap =
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, image)

            } else {
                imageBitmap =
                    ImageDecoder.createSource(requireContext().contentResolver, image).let {
                        ImageDecoder.decodeBitmap(it)
                    }
            }

            val tempFile =
                File.createTempFile("Img_", ".jpeg", requireActivity().cacheDir).also {
                    it.deleteOnExit()
                }


            val stream = FileOutputStream(tempFile)
            imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()

            return tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    private fun setupActivityResultLauncher() {

        //on permission request result
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {

                if (it) {
                    cameraLauncher.launch(image)
                }

            }

        //on gallery image back
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            if (uri != null) {
                image = uri
                viewModel.setImageName(uri, requireActivity())
            }
        }

        //on camera image back
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it)
                viewModel.setImageName(image.lastPathSegment ?: "")
        }

    }


    private fun launchCamera() {

        //check if permission is granted
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            cameraLauncher.launch(image)

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            )
        ) {  // if permission denied show dialog
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            builder.setMessage(ImageSelectionMethodConstants.permissionDeniedDialogMessage)
                .setTitle(ImageSelectionMethodConstants.permissionDeniedDialogTitle)
                .setPositiveButton(
                    android.R.string.ok
                ) { _, _ ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.data = Uri.fromParts(
                        "package",
                        ImageSelectionMethodConstants.applicationID, null
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                .setNegativeButton(
                    android.R.string.cancel
                ) { dialog, _ -> dialog?.dismiss() }
                .create()
                .show()
        } else { //request permission
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private var listener: ((Pair<String, File>) -> Unit)? = null

        fun setupListener(listener: (Pair<String, File>) -> Unit) {
            Companion.listener = listener
        }
    }

}