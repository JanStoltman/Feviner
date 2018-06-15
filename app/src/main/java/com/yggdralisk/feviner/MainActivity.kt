package com.yggdralisk.feviner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import com.yggdralisk.feviner.api.calls.NumbersServiceCalls.Companion.getManyRandomNumbers
import com.yggdralisk.feviner.custom.ObservableVariable
import com.yggdralisk.feviner.models.NumberModel
import com.yggdralisk.feviner.models.NumbersListModel
import com.yggdralisk.feviner.models.UserScore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    companion object {
        const val FETCH_SIZE = 20

    }

    @Inject
    lateinit var userScore: UserScore
    val compositeDisposable = CompositeDisposable()
    val numbers = NumbersListModel(arrayListOf())
    var observableNum = ObservableVariable<NumberModel?>(null)

    override fun onDestroy() {
        if (compositeDisposable.isDisposed.not()) {
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initializeObservers()
        fetchNumbers()
    }

    private fun initializeObservers() {
      /*  compositeDisposable.addAll(
                RxTextView.editorActions(answerEditText)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .filter { actionId -> actionId == EditorInfo.IME_ACTION_SEND }
                        .subscribe { confirmButton.callOnClick() },

                RxRadioGroup.checkedChanges(chosingRadioGroup)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe { handleModeChange(it) },

                rxThrottledClicks(confirmButton)
                        .subscribe { handleAnswer() },

                rxThrottledClicks(menuFetchNumbers)
                        .subscribe { fetchNumbers() },

                rxThrottledClicks(showAnswerButton)
                        .subscribe { showAnswer() },

                observableNum.observable
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe { num -> bindNewNumToView(num) },

                numbers.sizeObservable
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe { s -> if (s < FETCH_SIZE.div(2)) fetchNumbers() }
        )*/
    }

    private fun showAnswer() {
        observableNum.value?.let {
            val text = getString(R.string.correct_answer, observableNum.value?.getText(isAnswerAsNumModeActive()))
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()

            scoreTextView.text = getString(R.string.score, --userScore.score)
        }
    }

    private fun bindNewNumToView(num: NumberModel?) {
        if (isAnswerAsNumModeActive()) questionTextView.text = num?.latinNumber ?: ""
        else questionTextView.text = num?.arabicNumber?.toString() ?: ""
    }

    private fun handleModeChange(it: Int?) {
        when (it) {
            R.id.chosingNumberButton -> {
                setGameView(observableNum.value?.latinNumber ?: "", InputType.TYPE_CLASS_NUMBER)
            }
            R.id.chosingTextButton -> {
                setGameView(observableNum.value?.arabicNumber?.toString()
                        ?: "", InputType.TYPE_CLASS_TEXT)
            }
            else -> throw Exception("Unknown view in radio group: $it")
        }
    }

    private fun setGameView(text: String, typeClass: Int) {
        questionTextView.text = text
        answerEditText.inputType = typeClass
        answerEditText.text.clear()
    }

    private fun handleAnswer() {
        if (validateAnswer()) {
            scoreTextView.text = getString(R.string.score, ++userScore.score)
            answerEditText.text.clear()
            observableNum.value = numbers.getRandomNumber()
        } else {
            answerEditText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.wobble))
            questionTextView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.wobble))
        }
    }

    private fun fetchNumbers() {
        menuInternetError.visibility = View.GONE
        menuFetchNumbers.visibility = View.GONE
        menuProgressSpinner.visibility = View.VISIBLE

        getManyRandomNumbers(FETCH_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { l: List<NumberModel> ->
                            numbers.appendNumbers(l)
                            if (observableNum.value == null && numbers.isNotEmpty()) observableNum.value = numbers.getRandomNumber()

                            menuProgressSpinner.visibility = View.GONE
                        },
                        { e: Throwable ->
                            e.printStackTrace()
                            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_LONG).show()

                            menuProgressSpinner.visibility = View.GONE
                            menuInternetError.visibility = View.VISIBLE
                            menuFetchNumbers.visibility = View.VISIBLE
                        })
    }

    private fun validateAnswer(): Boolean = observableNum.value?.validateAnswer(answerEditText.text, isAnswerAsNumModeActive())
            ?: false

    private fun isAnswerAsNumModeActive() = chosingNumberButton.isChecked

    private fun rxThrottledClicks(view: View, throttleSeconds: Long = 2): Observable<Any> =
            RxView.clicks(view)
                    .throttleFirst(throttleSeconds, TimeUnit.SECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())

}