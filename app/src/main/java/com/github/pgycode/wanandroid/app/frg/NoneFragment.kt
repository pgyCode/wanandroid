package com.github.pgycode.wanandroid.app.frg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.pgycode.wanandroid.app.R
import com.github.pgycode.wanandroid.common.BaseFragment

/**
 * @author: xuxiaojie
 */

class NoneFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.frg_none, null)

}