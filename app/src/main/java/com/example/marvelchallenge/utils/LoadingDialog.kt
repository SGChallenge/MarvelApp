package com.example.marvelchallenge.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.marvelchallenge.R

class LoadingDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        return inflater.inflate(R.layout.layout_loading, container, false)
    }

    fun observeLiveData(data: LiveData<Boolean>, lo: LifecycleOwner, fm: FragmentManager) {
        data.observe(
            lo,
            {
                if (it) {
                    if (!isAdded) {
                        showNow(fm, null)
                    }
                } else {
                    if (isAdded) {
                        dismiss()
                    }
                }
            }
        )
    }
}
