package ru.androidschool.intensiv.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.SearchToolbarBinding
import ru.androidschool.intensiv.ui.common.OnBackButtonClickListener
import ru.androidschool.intensiv.utils.extensions.afterTextChanged
import java.util.concurrent.TimeUnit

class SearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    lateinit var binding: SearchToolbarBinding
    private var hint: String = ""
    private var isCancelVisible: Boolean = true
    private var clearButtonClickListener: OnBackButtonClickListener? = null

    private val searchSubject: PublishSubject<String> by lazy {
        PublishSubject.create()
    }

    init {
        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.SearchBar).apply {
                hint = getString(R.styleable.SearchBar_hint).orEmpty()
                isCancelVisible = getBoolean(R.styleable.SearchBar_cancel_visible, true)
                recycle()
            }
        }
    }

    fun setText(text: String?) {
        binding.searchEditText.setText(text)
    }

    fun clear() {
        binding.searchEditText.setText("")
    }

    private fun clearBackButton() {
        clear()
        clearButtonClickListener?.onClearButtonClicked()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = SearchToolbarBinding.inflate(LayoutInflater.from(context), this, true)
        binding.searchEditText.hint = hint
        binding.deleteTextButton.setOnClickListener {
            clearBackButton()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.searchEditText.afterTextChanged { text ->
            searchSubject.onNext(text.toString())
            if (!text.isNullOrEmpty() && !binding.deleteTextButton.isVisible) {
                binding.deleteTextButton.visibility = View.VISIBLE
            }
            if (text.isNullOrEmpty() && binding.deleteTextButton.isVisible) {
                binding.deleteTextButton.visibility = View.GONE
            }
        }
    }

    fun observeSearchTextWithFilter(): Observable<String> {
        return searchSubject
            .debounce(500, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .filter { it.length > MIN_LENGTH }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun setOnClearButtonClickListener(listener: OnBackButtonClickListener) {
        clearButtonClickListener = listener
    }

    companion object {
        const val MIN_LENGTH = 3
    }
}
