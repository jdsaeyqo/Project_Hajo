package com.ssafy.hajo.plan

import DiaryObject
import DiaryViewModel
import TextObjectInfo
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.FragmentEditTextBinding

@RequiresApi(Build.VERSION_CODES.O)
class editTextFragment (context: Context) : BottomSheetDialogFragment() {
    private val mContext: Context = context
    private lateinit var binding: FragmentEditTextBinding
    private val viewModel: DiaryViewModel by activityViewModels()

    private var textObj: DiaryObject = DiaryObject(0,0,"",0,
    0, "", TextObjectInfo())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(viewModel.EditText.value == null) {
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }

        Log.d("editText","기본 값 $textObj")
        Log.d("editText","view moel 값 ${viewModel.EditText.value}")


        textObj = viewModel.EditText.value!! // 변경할 값 설정

        initEditText()
        initColorButton()
        initStyleButton()

        binding.btnSave.setOnClickListener {
            textObj.objtext.objtextContent = binding.etEditText.text.toString()
            viewModel.setEditText(textObj)
            viewModel.updateTextObj(textObj)
            requireFragmentManager().beginTransaction().remove(this).commit();
            viewModel.resetEditTextResponse()

        }

        binding.btnCancel.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
            viewModel.resetEditTextResponse()
        }

        viewModel.EditText.observe(this) {
            if(viewModel.EditTextResponse.value == 0){

            } else if(viewModel.EditTextResponse.value == 200) {
                val fragmentManager = requireActivity().supportFragmentManager;
                fragmentManager.beginTransaction().remove(this).commit();
                viewModel.resetEditTextResponse()
            } else {
                viewModel.resetEditTextResponse()
            }
        }



    }

    @SuppressLint("Range")
    private fun initEditText() {
        binding.etEditText.setText(textObj.objtext.objtextContent)
        binding.etEditText.setTextColor(Color.parseColor(textObj.objtext.objtextColor))
        binding.etEditText.setTextSize(textObj.objtext.objtextSize.toFloat())
        Log.d("font", textObj.objtext.objtextFont)
        when(textObj.objtext.objtextFont) {
            "0" -> {
                Log.d("font", textObj.objtext.objtextFont)

                val typeface = resources.getFont(R.font.kotra_hope)
                binding.etEditText.typeface = typeface
            }
            "1" -> {
                Log.d("font", textObj.objtext.objtextFont)

                val typeface = resources.getFont(R.font.cookie_run_regular)
                binding.etEditText.typeface = typeface
            }
            "2" -> {
                Log.d("font", textObj.objtext.objtextFont)
                val typeface = resources.getFont(R.font.nexon_lv2_gothic_bold)
                binding.etEditText.typeface = typeface
            }
        }
        if(textObj.objtext.objtextIsbold) {
            binding.etEditText.setTypeface(null, Typeface.BOLD)
        }
    }

    private fun initColorButton() {

        binding.imgRed.setOnClickListener {
            textObj.objtext.objtextColor = "#F0D64531"
            binding.etEditText.setTextColor(Color.parseColor(textObj.objtext.objtextColor))
        }

        binding.imgBlack.setOnClickListener {
            textObj.objtext.objtextColor = "#DF1717"
            binding.etEditText.setTextColor(Color.parseColor(textObj.objtext.objtextColor))
        }

        binding.imgBlue.setOnClickListener {
            textObj.objtext.objtextColor = "#FF374EBF"
            binding.etEditText.setTextColor(Color.parseColor(textObj.objtext.objtextColor))
        }

        binding.imgPrimary.setOnClickListener {
            textObj.objtext.objtextColor = "#FFF5B51D"
            binding.etEditText.setTextColor(Color.parseColor(textObj.objtext.objtextColor))
        }

        binding.imgGreen.setOnClickListener {
            textObj.objtext.objtextColor = "#FF28BC45"
            binding.etEditText.setTextColor(Color.parseColor(textObj.objtext.objtextColor))
        }

        binding.imgWhite.setOnClickListener {
            textObj.objtext.objtextColor = "#FFFFFFFF"
            binding.etEditText.setTextColor(Color.parseColor(textObj.objtext.objtextColor))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun  initStyleButton() {
        binding.sizeUp.setOnClickListener {
            textObj.objtext.objtextSize += 5
            binding.etEditText.setTextSize(textObj.objtext.objtextSize.toFloat())
        }

        binding.sizeDown.setOnClickListener {
            textObj.objtext.objtextSize -= 5
            binding.etEditText.setTextSize(textObj.objtext.objtextSize.toFloat())
        }

        binding.bold.setOnClickListener {
            if(textObj.objtext.objtextIsbold) {
                binding.etEditText.setTypeface(null, Typeface.NORMAL)
                textObj.objtext.objtextIsbold = !textObj.objtext.objtextIsbold
            } else{
                binding.etEditText.setTypeface(null, Typeface.BOLD)
                textObj.objtext.objtextIsbold = !textObj.objtext.objtextIsbold
            }
        }
        binding.font1.setOnClickListener {
            textObj.objtext.objtextFont = "0"
            val typeface = resources.getFont(R.font.kotra_hope)
            binding.etEditText.typeface = typeface
        }

        binding.font2.setOnClickListener {
            textObj.objtext.objtextFont = "1"
            val typeface = resources.getFont(R.font.cookie_run_regular)
            binding.etEditText.typeface = typeface
        }

        binding.font3.setOnClickListener {
            textObj.objtext.objtextFont = "2"
            val typeface = resources.getFont(R.font.nexon_lv2_gothic_bold)
            binding.etEditText.typeface = typeface
        }
    }
}